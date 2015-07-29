package com.yuelian.qqemotion.android.bbs.fragment;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yuelian.qqemotion.android.bbs.activity.SelectMorePicActivity;
import com.yuelian.qqemotion.android.bbs.manager.TopicDetailManager;
import com.yuelian.qqemotion.android.bbs.model.TopicCommentInfo;
import com.yuelian.qqemotion.android.bbs.model.TopicDetailInfo;
import com.yuelian.qqemotion.android.bbs.model.TopicInfo;
import com.yuelian.qqemotion.android.bbs.widget.DisableHorizontalScrollView;
import com.yuelian.qqemotion.android.emotion.activities.SendToActivity;
import com.yuelian.qqemotion.android.emotion.adapter.BasePkgDetailAdapter;
import com.yuelian.qqemotion.android.emotion.helper.EmotionModeHelper;
import com.yuelian.qqemotion.android.emotion.service.EmotionDownloadService;
import com.yuelian.qqemotion.android.framework.logger.LoggerFactory;
import com.yuelian.qqemotion.android.framework.net.YuelianResponse;
import com.yuelian.qqemotion.android.framework.utils.StringUtils;
import com.yuelian.qqemotion.android.recent.db.RecentEmotDao;
import com.yuelian.qqemotion.android.statistics.service.StatisticService;
import com.yuelian.qqemotion.android.user.manager.UserManager;
import com.yuelian.qqemotion.android.user.model.UserInfo;
import com.yuelian.qqemotion.db.DaoFactory;
import com.yuelian.qqemotion.jgzemotion.EmotionViewUtil;
import com.yuelian.qqemotion.jgzfight.model.BaseFightViewModel;
import com.yuelian.qqemotion.jgzfight.model.EmotionFightModel;
import com.yuelian.qqemotion.jgzfight.model.TextFightModel;
import com.yuelian.qqemotion.jgzfight.model.TimeFightModel;
import com.yuelian.qqemotion.jgzhome.viewmodel.LiveGifEmotionViewHolder;
import com.bugua.fight.R;
import com.yuelian.qqemotion.service.file.ArchiveUtils;
import com.yuelian.qqemotion.umeng.UmengFragment;
import com.yuelian.qqemotion.utils.Globals;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.core.Arrays;
import net.tsz.afinal.core.AsyncTask;

import org.slf4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by woookliu on 11/13/14.
 * 帖子详情页面
 */
public class TopicDetailFragment extends UmengFragment implements BasePkgDetailAdapter.IDownloadingLiveViewHandler {

    private Logger logger = LoggerFactory.getLogger("TopicDetailFragment");

    public static final String EXTRA_REPLY_TEXT = "replyText";
    public static final String EXTRA_REPLY_USER_ID = "userId";

    private long topicId;

    private long ownerId;   //楼主ID
    private long localUserId;   //当前用户ID
    private List<BaseFightViewModel> fightViewModelList;
    private List<BaseFightViewModel> tmpModelList = new ArrayList<>();
    private PullToRefreshListView commentListView;
    private BaseAdapter adapter;

    private DisableHorizontalScrollView horizontalScrollView;
    private FrameLayout txtSendEmotContainer;
    private TextView txtSendEmot;
    private View txtSendEmotRight;
    private TextView btnSendEmot;
    private View txtSendMsgLeft;
    private EditText txtSendMsg;
    private TextView btnSendMsg;

    private int txtSendEmotShortWidth;
    private int txtSendEmotLongWidth;
    private int etSendMsgShortWidth;
    private int etSendMsgLongWidth;

    private MyAdapter emotionAdapter;

    private FinalBitmap fb;

    private TextView commentEmotionCountStr;
    private GridView emotionContainer;

    private static final int REQUEST_CODE_SEND_COMMENT = 0;
    private static final int REQUEST_CODE_ADD_EMOTION = 1;

    private static final String EXTRA_TOPIC_ID = "topicId";

    public static TopicDetailFragment getFragment(long topicId) {
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_TOPIC_ID, topicId);
        TopicDetailFragment fragment = new TopicDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        IntentFilter intentFilter = new IntentFilter(EmotionDownloadService.ACTION_DOWNLOAD_FRONTEND_FINISH);
        getActivity().registerReceiver(packageDownloadStatusReceiver, intentFilter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localUserId = new UserManager().loadUserInfo(getActivity()).getId();
        fb = FinalBitmap.create(getActivity());
        topicId = getArguments().getLong(EXTRA_TOPIC_ID);
        final Context context = getActivity();
        final ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.primary));
        adapter = new BaseAdapter() {

            private Resources resources = getResources();

            private static final int TYPE_TEXT = 0;
            private static final int TYPE_EMOTION = 1;
            private static final int TYPE_TIME = 2;
            private static final int TYPE_COUNT = 3;


            private String currentEmotionFileName;      //列表样式使用，纪录当前选中的图片，直接发送按钮使用
            private boolean onlyRefreshLiveButton = false;  //只刷新直接发送按钮的状态。防止图片重新加载。没有想到更好的方案

            private View.OnClickListener onClickListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.emot_box:
                            String fileName = (String) v.getTag();
                            if (fileName != null
                                    && fileName.equals(currentEmotionFileName)) {
                                currentEmotionFileName = null;
                            } else {
                                currentEmotionFileName = fileName;
                            }
                            onlyRefreshLiveButton = true;
                            notifyDataSetChanged();
                            v.post(new Runnable() {
                                @Override
                                public void run() {
                                    onlyRefreshLiveButton = false;  //刷新完毕后回复正常状态。还是比较危险的，有问题的话只能用ContentMenu了
                                }
                            });
                            break;
                        case R.id.btn_send:
                            File currentFile = (File) v.getTag();
                            if (currentFile.exists()) {
                                if (!mRecentEmotDao.isExist(currentFile.getName())) {
                                    mRecentEmotDao.insert(new RecentEmotDao.RecentEmotion(currentFile.getName()));
                                }
                                getActivity().startActivity(SendToActivity.newEmotionIntent(getActivity(), currentFile.getAbsolutePath(), StatisticService.PreviewFrom.emotList));
                            } else {
                                noFile();
                            }
                            break;
                    }
                }
            };

            private RecentEmotDao mRecentEmotDao = DaoFactory.createRecentEmotDao();

            private void noFile() {
                Toast.makeText(getActivity(), getString(R.string.txt_not_downloaded), Toast.LENGTH_SHORT).show();
            }

            @Override
            public int getViewTypeCount() {
                return TYPE_COUNT;
            }

            @Override
            public int getItemViewType(int position) {
                switch (getItem(position).getType()) {
                    case text:
                        return TYPE_TEXT;
                    case emotion:
                        return TYPE_EMOTION;
                    case time:
                        return TYPE_TIME;
                    default:
                        return -1;
                }
            }

            @Override
            public int getCount() {
                if (fightViewModelList == null) {
                    return 0;
                } else {
                    return fightViewModelList.size() + tmpModelList.size();
                }
            }

            @Override
            public BaseFightViewModel getItem(int position) {
                if (position >= fightViewModelList.size()) {
                    return tmpModelList.get(position - fightViewModelList.size());
                } else {
                    return fightViewModelList.get(position);
                }
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                switch (getItemViewType(position)) {
                    case TYPE_TIME:
                        if (convertView == null) {
                            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_feed_time, parent, false);
                            convertView.setTag(convertView.findViewById(R.id.tv_time));
                        }
                        TimeFightModel model = (TimeFightModel) getItem(position);
                        ((TextView) convertView.getTag()).setText(StringUtils.getTimeStr(model.getTime()));
                        break;
                    case TYPE_EMOTION:
                        if (convertView == null) {
                            convertView = EmotionViewUtil.createEmotionView(
                                    parent,
                                    TopicDetailFragment.this,
                                    onClickListener,
                                    getActivity()
                            );
                        }
                        LiveGifEmotionViewHolder liveGifEmotionViewHolder = (LiveGifEmotionViewHolder) convertView.getTag();
                        EmotionFightModel emotionFightModel = (EmotionFightModel) getItem(position);
                        EmotionViewUtil.handleEmotionView(
                                getActivity(),
                                fb,
                                liveGifEmotionViewHolder,
                                emotionFightModel,
                                currentEmotionFileName,
                                position,
                                onlyRefreshLiveButton
                        );
                        liveGifEmotionViewHolder.userName.setVisibility(View.VISIBLE);
                        if (emotionFightModel.getUserId() == localUserId) {
                            SpannableStringBuilder ssb = new SpannableStringBuilder(emotionFightModel.getUserName());
                            SpannableString mine = new SpannableString("(我)");
                            mine.setSpan(foregroundColorSpan, 0, mine.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            ssb.append(mine);
                            liveGifEmotionViewHolder.userName.setText(ssb);
                        } else {
                            liveGifEmotionViewHolder.userName.setText(emotionFightModel.getUserName());
                        }
                        break;
                    case TYPE_TEXT:
                        if (convertView == null) {
                            convertView = EmotionViewUtil.createMessageView(
                                    parent,
                                    getActivity()
                            );
                        }
                        TextFightModel textFightModel = (TextFightModel) getItem(position);
                        EmotionViewUtil.TextMessageViewHolder viewHolder = (EmotionViewUtil.TextMessageViewHolder) convertView.getTag();
                        EmotionViewUtil.handleMessageView(getActivity(),
                                fb,
                                viewHolder,
                                textFightModel);
                        viewHolder.userName.setVisibility(View.VISIBLE);
                        if (textFightModel.getUserId() == localUserId) {
                            SpannableStringBuilder ssb = new SpannableStringBuilder(textFightModel.getName());
                            SpannableString mine = new SpannableString("(我)");
                            mine.setSpan(foregroundColorSpan, 0, mine.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            ssb.append(mine);
                            viewHolder.userName.setText(ssb);
                        } else {
                            viewHolder.userName.setText(textFightModel.getName());
                        }
                        break;
                }
                return convertView;
            }

            private void setEmotion(FinalBitmap fb, TopicInfo topicInfo, LinearLayout line1, LinearLayout line2) {
                int count = topicInfo.getEmotionsArray().length;
                if (count == 0) {
                    line1.setVisibility(View.GONE);
                    line2.setVisibility(View.GONE);
                    return;
                } else {
                    line1.setVisibility(View.VISIBLE);
                    if (count > 4) {
                        line2.setVisibility(View.VISIBLE);
                    }
                }
                ViewGroup[] emotionContainers = new ViewGroup[8];
                for (int i = 0; i < 4; i++) {
                    emotionContainers[i] = (ViewGroup) line1.getChildAt(i * 2);
                    emotionContainers[i + 4] = (ViewGroup) line2.getChildAt(i * 2);
                }
                for (int i = 0; i < 8; i++) {

                    //http://pic.bugua.com/a9fdac230f65c76d9fcbd33ee8398bcb.jpg@0e_100w_100h_0c_0i_1o_90Q_1x.jpg
                    String thumbUrl = topicInfo.getEmotThumbUrl(i);
                    if (thumbUrl == null) {
                        emotionContainers[i].setVisibility(View.INVISIBLE);
                    } else {
                        ImageView emotionThumbView = (ImageView) emotionContainers[i].getChildAt(0);    //跟布局耦合
                        emotionThumbView.setTag(topicInfo.getEmotPath(i));
                        fb.display(emotionThumbView, thumbUrl, resources);
                    }
                }

            }

        };
        new LoadDataTask().execute(topicId);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().unregisterReceiver(packageDownloadStatusReceiver);
    }

    @Override
    public void onDestroy() {
        for (LoadDataTask task : taskSet) {
            if (task.getStatus() != AsyncTask.Status.FINISHED) {
                logger.debug("取消任务");
                task.cancel(true);
            }
        }
        if (loadMoreTask != null) {
            loadMoreTask.cancel(true);
        }
        super.onDestroy();
    }

    private File getEmotionFile(String emotPath) {
        return new File(ArchiveUtils.getEmotDir(getActivity()), emotPath);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_topic_detail, container, false);

        horizontalScrollView = (DisableHorizontalScrollView) view.findViewById(R.id.scrollView);
        txtSendEmotContainer = (FrameLayout) horizontalScrollView.findViewById(R.id.txt_send_emot_container);
        txtSendEmot = (TextView) horizontalScrollView.findViewById(R.id.txt_send_emot);
        txtSendEmotContainer.setOnClickListener(onTxtSendListener);
        txtSendEmotRight = horizontalScrollView.findViewById(R.id.txt_send_emot_right);
        btnSendEmot = (TextView) horizontalScrollView.findViewById(R.id.btn_send_emot);
        btnSendEmot.setOnClickListener(onTxtSendListener);
        txtSendMsgLeft = horizontalScrollView.findViewById(R.id.txt_send_msg_left);
        txtSendMsg = (EditText) horizontalScrollView.findViewById(R.id.txt_send_msg);
        txtSendMsg.addTextChangedListener(textWatcher);
        btnSendMsg = (TextView) horizontalScrollView.findViewById(R.id.btn_send_msg);
        btnSendMsg.setOnClickListener(onTxtSendListener);
        txtSendEmotRight.setOnClickListener(onTxtSendListener);
        txtSendMsgLeft.setOnClickListener(onTxtSendListener);
        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (horizontalScrollView.getScrollX() < txtSendEmotLongWidth / 2) {
                            horizontalScrollView.fullScroll(View.FOCUS_LEFT);
                            hideKeyBoard();
                        } else {
                            horizontalScrollView.fullScroll(View.FOCUS_RIGHT);
                            txtSendMsg.performClick();
                        }
                        return true;
                }
                return false;
            }
        });
        horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                int space = getActivity().getResources().getDimensionPixelOffset(R.dimen.fragment_reply_space);
                int totalWidth = horizontalScrollView.getWidth();   //总长度
                int btnSendWidth = btnSendEmot.getMeasuredWidth();  //发送按钮宽度
                int txtSendEmotionRightWidth = txtSendEmotRight.getWidth(); //发送图片右边圆角宽度
                int txtSendMsgLeftWidth = txtSendMsgLeft.getWidth();    //发送文字左边圆角宽度
                txtSendEmotLongWidth = totalWidth - txtSendEmotionRightWidth - txtSendMsgLeftWidth - 2 * space;
                txtSendEmotShortWidth = totalWidth - txtSendEmotionRightWidth - btnSendWidth - 2 * space;
                etSendMsgLongWidth = totalWidth - txtSendEmotionRightWidth - txtSendMsgLeftWidth - 2 * space;
                etSendMsgShortWidth = totalWidth - txtSendMsgLeftWidth - btnSendWidth - 2 * space;
                setInitStatus(true);
            }
        });

        commentListView = (PullToRefreshListView) view.findViewById(R.id.lv_topic_comments);
        commentListView.getRefreshableView().setSelector(R.color.transparent);
        commentListView.setAdapter(adapter);
        commentListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDataTask().execute();
            }
        });
        commentListView.setEmptyView(inflater.inflate(R.layout.loading, null));
        addFooter(inflater, getResources(), commentListView);
        emotionContainer = (GridView) view.findViewById(R.id.grid_topic_detail);
        emotionAdapter = new MyAdapter();
        emotionContainer.setAdapter(emotionAdapter);
        commentEmotionCountStr = (TextView) view.findViewById(R.id.txt_emotion_selected_count);
        setEmotionCountText(0);
        return view;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String t = editable.toString();
            if ("".equals(t)) {
                setInitStatus(false);
            } else {
                showSendMsgButton();
            }
        }
    };

    /**
     * 初始化状态
     */
    private void setInitStatus(boolean scrollToBegin) {
        horizontalScrollView.setScrollable(true);
        //设置发表情宽度
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) txtSendEmotContainer.getLayoutParams();
        lp.width = txtSendEmotLongWidth;
        txtSendEmotContainer.setLayoutParams(lp);
        //设置发文字宽度
        LinearLayout.LayoutParams msgLp = (LinearLayout.LayoutParams) txtSendMsg.getLayoutParams();
        msgLp.width = etSendMsgLongWidth;
        txtSendMsg.setLayoutParams(msgLp);
        //隐藏发送按钮
        btnSendEmot.setVisibility(View.GONE);
        btnSendMsg.setVisibility(View.GONE);
        //滑动到开始位置
        if (scrollToBegin) {
            horizontalScrollView.fullScroll(View.FOCUS_LEFT);
        } else {
            horizontalScrollView.fullScroll(View.FOCUS_RIGHT);
        }
        if (Build.VERSION.SDK_INT >= 11) {
            ((LinearLayout) horizontalScrollView.findViewById(R.id.content)).setLayoutTransition(new LayoutTransition());
        }
        hideEmotionArea();
    }

    /**
     * 显示准备发布表情的状态
     */
    private void showSendEmotionButton() {
        horizontalScrollView.setScrollable(false);
        //设置发表请宽度
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) txtSendEmotContainer.getLayoutParams();
        lp.width = txtSendEmotShortWidth;
        txtSendEmotContainer.setLayoutParams(lp);
        //设置发文字宽度
        LinearLayout.LayoutParams msgLp = (LinearLayout.LayoutParams) txtSendMsg.getLayoutParams();
        msgLp.width = etSendMsgShortWidth;
        txtSendMsg.setLayoutParams(msgLp);
        //显示发送按钮
        btnSendEmot.setVisibility(View.VISIBLE);
        //隐藏文字发送按钮
        btnSendMsg.setVisibility(View.GONE);
        txtSendMsg.removeTextChangedListener(textWatcher);
        txtSendMsg.setText("");
        txtSendMsg.addTextChangedListener(textWatcher);
        horizontalScrollView.fullScroll(View.FOCUS_LEFT);
        //显示表情选择
        showEmotionArea();
    }

    /**
     * 显示准备发布文字的状态
     */
    private void showSendMsgButton() {
        horizontalScrollView.setScrollable(false);
        //设置发表请宽度
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) txtSendEmotContainer.getLayoutParams();
        lp.width = txtSendEmotShortWidth;
        txtSendEmotContainer.setLayoutParams(lp);
        //设置发文字宽度
        LinearLayout.LayoutParams msgLp = (LinearLayout.LayoutParams) txtSendMsg.getLayoutParams();
        msgLp.width = etSendMsgShortWidth;
        txtSendMsg.setLayoutParams(msgLp);
        //隐藏表情发送按钮
        btnSendEmot.setVisibility(View.GONE);
        //显示文字发送按钮
        btnSendMsg.setVisibility(View.VISIBLE);
        list.clear();
        emotionAdapter.notifyDataSetChanged();
        refreshEmotionCountStatus();
        //显示表情选择
        hideEmotionArea();
        horizontalScrollView.fullScroll(View.FOCUS_RIGHT);
    }

    private View.OnClickListener onTxtSendListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.txt_send_emot_container:
                    if (emotionContainer.getVisibility() == View.VISIBLE) {
                        if (list.size() > 0) {
                            hideEmotionArea();
                        } else {
                            setInitStatus(true);
                        }
                    } else {
                        showSendEmotionButton();
                    }
                    break;
                case R.id.btn_send_msg:
                case R.id.btn_send_emot:
                    submitComment();
                    break;
                case R.id.txt_send_emot_right:
                    horizontalScrollView.fullScroll(View.FOCUS_LEFT);
                    break;
                case R.id.txt_send_msg_left:
                    horizontalScrollView.fullScroll(View.FOCUS_RIGHT);
                    break;
            }
        }
    };

    private Set<LoadDataTask> taskSet = new HashSet<>();

    private Map<String, LiveGifEmotionViewHolder> downloadingLiveViewCache = new HashMap<String, LiveGifEmotionViewHolder>();

    @Override
    public void cacheView(String emotFileName, LiveGifEmotionViewHolder viewHolder) {
        String oldKey = viewHolder.showingFileName;
        if (oldKey != null) {
            downloadingLiveViewCache.remove(oldKey);
        }
        viewHolder.showingFileName = emotFileName;
        downloadingLiveViewCache.put(emotFileName, viewHolder);
    }

    private class LoadDataTask extends AsyncTask<Long, Void, YuelianResponse<TopicDetailInfo>> {

        private Context context;

        @Override
        protected void onPreExecute() {
            taskSet.add(this);
            super.onPreExecute();
            context = getActivity().getApplicationContext();
        }

        @Override
        protected YuelianResponse<TopicDetailInfo> doInBackground(Long... params) {
            return new TopicDetailManager().getTopicDetailInfo(context, topicId);
        }

        @Override
        protected void onPostExecute(final YuelianResponse<TopicDetailInfo> response) {
            if (response.isSuccessful()) {
                TopicDetailInfo detailInfo = response.getResult();
                UserInfo owner = detailInfo.getOwnerInfo();
                ownerId = owner.getId();
                fightViewModelList = new ArrayList<>();
                TopicInfo topicInfo = detailInfo.getTopicInfo();
                fightViewModelList.add(new TimeFightModel(owner.getId(), (int) topicInfo.getId(), topicInfo.getTimestamp()));
                tmpModelList.clear();
                String userAvatar = owner.getAvatar();
                String userName = owner.getName();
                String content = topicInfo.getContent();
                addTextFightModel(owner.getId(), (int) topicInfo.getId(), topicInfo.getTitle(), userAvatar, owner.getName());
                addTextFightModel(owner.getId(), (int) topicInfo.getId(), content, userAvatar, owner.getName());
                String[] emotions = topicInfo.getEmotionsArray();
                if (emotions != null && emotions.length > 0) {
                    for (String e : emotions) {
                        fightViewModelList.add(new EmotionFightModel(owner.getId(), (int) topicInfo.getId(), userAvatar, e, userName));
                    }
                    EmotionDownloadService.downloadEmotionArray(getActivity(), MOCK_PACKAGE_ID, emotions);
                }
                List<TopicCommentInfo> commentInfoList = detailInfo.getCommentList();
                if (commentInfoList.size() == 0) {
                    setNoMore();
                } else {
                    addCommentList(commentInfoList);
                    setNextPage(commentInfoList.get(commentInfoList.size() - 1).getId());
                }
                commentListView.onRefreshComplete();
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(context, response.getError().getErrorMessage(context), Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
            taskSet.remove(this);
        }
    }

    private void addTextFightModel(long userId, int messageId, String text, String avatar, String name) {
        if (!text.isEmpty()) {
            fightViewModelList.add(new TextFightModel(userId, messageId, text, avatar, name));
        }
    }

    private void addCommentList(List<TopicCommentInfo> list) {
        tmpModelList.clear();
        for (TopicCommentInfo topicCommentInfo : list) {
            long userId = topicCommentInfo.getUserInfo().getId();
            String avatar = topicCommentInfo.getUserInfo().getAvatar();
            String userName = topicCommentInfo.getUserInfo().getName();
            String t = topicCommentInfo.getContent();
            String[] emotions = topicCommentInfo.getEmotions();
            if (!t.isEmpty()
                    || (emotions != null && emotions.length > 0)) {
                fightViewModelList.add(new TimeFightModel(userId, (int) topicCommentInfo.getId(), topicCommentInfo.getTime()));
            }
            addTextFightModel(userId, (int) topicCommentInfo.getId(), t, avatar, userName);
            if (emotions != null) {

                for (String e : emotions) {
                    fightViewModelList.add(
                            new EmotionFightModel(userId, (int) topicCommentInfo.getId(), avatar, e, userName)
                    );
                }
            }
            EmotionDownloadService.downloadEmotionArray(getActivity(), MOCK_PACKAGE_ID, emotions);
        }
    }

    private class CommentViewHandler {
        public ImageView avatar;    //头像
        public TextView commentIndex;   //评论楼数
        public TextView commentTime;    //评论时间
        public TextView commentUserName;    //评论者用户名
        public TextView commentContent; //评论内容
        public LinearLayout emotionLine1;   //第一行4个表情
        public LinearLayout emotionLine2;   //第二行4个表情
        public FrameLayout[] emotionViewArray;    //表情的ImageView数组
        public TopicCommentInfo commentInfo;    //存储评论model
    }

    private LoadMoreTask loadMoreTask;
    private boolean hostOnly = false;

    private void loadMore(long lastCommentId) {
        if (loadMoreTask != null && loadMoreTask.getStatus() != AsyncTask.Status.FINISHED) {
            loadMoreTask.cancel(true);
        }
        loadMoreTask = new LoadMoreTask();
        loadMoreTask.execute(lastCommentId);
    }

    /**
     * 读取更多评论。如果lastId是－1，则按照是否只看楼主重新读取评论列表
     */
    private class LoadMoreTask extends AsyncTask<Long, Void, YuelianResponse<List<TopicCommentInfo>>> {

        @Override
        protected YuelianResponse<List<TopicCommentInfo>> doInBackground(Long... params) {
            return new TopicDetailManager().getMoreComments(getActivity(), topicId, params[0], hostOnly);
        }

        @Override
        protected void onPostExecute(YuelianResponse<List<TopicCommentInfo>> listYuelianResponse) {
            if (listYuelianResponse.isSuccessful()) {
                List<TopicCommentInfo> moreList = listYuelianResponse.getResult();
                if (moreList != null && moreList.size() > 0) {
                    addCommentList(moreList);
                    adapter.notifyDataSetChanged();
                    setNextPage(moreList.get(moreList.size() - 1).getId());
                } else {
                    setNoMore();
                }
            } else {
                Toast.makeText(getActivity(), listYuelianResponse.getError().getErrorMessage(getActivity()), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 显示表情选择区域
     */
    private ArrayList<String> list = new ArrayList<String>();

    private void showEmotionArea() {
        emotionContainer.setVisibility(View.VISIBLE);
        //TODO:设置GridView的Adapter
        if (emotionAdapter == null) {
            emotionAdapter = new MyAdapter();
        } else {
            emotionAdapter.notifyDataSetChanged();
        }
        emotionContainer.setAdapter(emotionAdapter);

        commentEmotionCountStr.setVisibility(View.VISIBLE);
        txtSendEmot.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hide_array, 0);
        refreshEmotionCountStatus();
    }

    private void hideEmotionArea() {
        emotionContainer.setVisibility(View.GONE);
        commentEmotionCountStr.setVisibility(View.GONE);
        txtSendEmot.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_array, 0);
    }

    /**
     * 提交评论
     */
    private void submitComment() {
        String userName = new UserManager().loadUserInfo(getActivity()).getName();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getActivity(), "请先设置昵称", Toast.LENGTH_SHORT).show();
            DialogChangeUserName.newInstance(userName.trim()).show(getActivity().getSupportFragmentManager(), "set name");
            return;
        }
        String comment = txtSendMsg.getEditableText().toString();
        if ((list == null || list.size() == 0)
                && comment.trim().length() == 0) {
            Toast.makeText(getActivity(), "请填写回复内容或者选择表情", Toast.LENGTH_SHORT).show();
            return;
        }
        DialogFragment fragment = SubmittingCommentDialogFragment.newInstance(
                topicId,
                ownerId,
                comment,
                list == null ? null : list.toArray(new String[list.size()])
        );
        fragment.setTargetFragment(this, REQUEST_CODE_SEND_COMMENT);
        fragment.show(getActivity().getSupportFragmentManager(), "commit");
        hideKeyBoard();
    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtSendMsg.getWindowToken(), 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADD_EMOTION:
                    list.clear();
                    list.addAll(data.getStringArrayListExtra(SelectMorePicActivity.IMG_URI_LIST));
                    refreshEmotionCountStatus();
                    emotionAdapter.notifyDataSetChanged();
                    emotionContainer.setAdapter(emotionAdapter);
                    break;
                case REQUEST_CODE_SEND_COMMENT:
                    initCommentLayout(data);
                    break;
            }
        }
    }

    /**
     * 初始化评论界面，隐藏评论框，刷新评论数据
     */
    private void initCommentLayout(Intent intent) {
        //本地添加一条到最后
        UserInfo localUser = new UserManager().loadUserInfo(getActivity());
        tmpModelList.add(new TimeFightModel(localUserId, (int) lastCommentId, new Date().getTime()));
        String text = txtSendMsg.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            tmpModelList.add(
                    new TextFightModel(
                            localUserId,
                            (int) lastCommentId,
                            text,
                            localUser.getAvatar(),
                            localUser.getName()
                    )
            );
        }
        txtSendMsg.removeTextChangedListener(textWatcher);
        txtSendMsg.setText("");
        txtSendMsg.addTextChangedListener(textWatcher);
        String[] serverEmotPathArray = intent.getStringArrayExtra(Globals.BBS_POST_COMMENT_PARAM_URLS);
        boolean scrollToBegin = false;
        if (serverEmotPathArray != null && serverEmotPathArray.length > 0) {
            for (String e:serverEmotPathArray) {
                tmpModelList.add(
                        new EmotionFightModel(
                                localUserId,
                                (int) lastCommentId,
                                localUser.getAvatar(),
                                e,
                                localUser.getName()
                        )
                );
            }
            EmotionDownloadService.downloadEmotionArray(getActivity(), MOCK_PACKAGE_ID, serverEmotPathArray);
            scrollToBegin = true;
        }
        adapter.notifyDataSetChanged();
        commentListView.getRefreshableView().smoothScrollToPosition(adapter.getCount());    //滚到最后
        list.clear();
        emotionAdapter.notifyDataSetChanged();
        refreshEmotionCountStatus();
        hideEmotionArea();
        setInitStatus(scrollToBegin);
    }

    private void refreshEmotionCountStatus() {
        if (list != null) {    //防止NullPointerException的补丁代码
            int count = list.size();
            setEmotionCountText(count);
        }
    }

    /**
     * 更新表情文件选择个数的文字
     *
     * @param count
     */
    private void setEmotionCountText(int count) {
        if (count > Globals.BBS_MAX_EMOTION_COUNT) {
            throw new IllegalArgumentException("Can not select more emotion files");
        }
        String emotionCountText;
        Resources resources = getResources();
        if (count == 0) {
            emotionCountText = String.format(resources.getString(R.string.bbs_new_topic_no_emotion_selected), Globals.BBS_MAX_EMOTION_COUNT);
            txtSendEmot.setText(R.string.send_emot);
        } else {
            emotionCountText = String.format(resources.getString(R.string.bbs_new_topic_some_emotion_selected), count, (Globals.BBS_MAX_EMOTION_COUNT - count));
            txtSendEmot.setText(resources.getString(R.string.bbs_comment_emotion_count, count, Globals.BBS_MAX_EMOTION_COUNT));
        }
        commentEmotionCountStr.setText(Html.fromHtml(emotionCountText));
    }

    private static final int MOCK_PACKAGE_ID = Integer.MAX_VALUE;

    private BroadcastReceiver packageDownloadStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            logger.debug("receive broadcast");
            if (EmotionDownloadService.ACTION_DOWNLOAD_FRONTEND_FINISH.equals(action)) {
                //预览表情前台任务下载完成
                logger.debug("接收到表情下载完成广播");
                int pkgId = intent.getIntExtra(EmotionDownloadService.EXTRA_PACKAGE_ID, -1);
                if (pkgId == MOCK_PACKAGE_ID) {
                    logger.debug("是当前表情包的表情");
                    String emotName = intent.getStringExtra(EmotionDownloadService.EXTRA_IMAGE_URL);
                    logger.debug("下载完成的表情文件名称为：" + emotName);
                    if (downloadingLiveViewCache.containsKey(emotName)) {
                        logger.debug("当前显示的缓存中有这个表情！显示");
                        LiveGifEmotionViewHolder viewHolder = downloadingLiveViewCache.get(emotName);
                        viewHolder.setEmotFile(getEmotionFile(emotName), emotName);
                    }
                }
            }
        }
    };

    //=============分页＝＝＝＝＝＝＝＝＝＝＝＝＝＝

    private long lastCommentId = -1;  //上一页最后一条评论

    private View footer;
    private View loadingMoreProgressBar;
    private TextView loadingMoreTextView;

    private void addFooter(LayoutInflater inflater, Resources resources, PullToRefreshListView listView) {
        footer = inflater.inflate(R.layout.search_loading_more, null, false);
        loadingMoreProgressBar = footer.findViewById(R.id.icon_loading);
        loadingMoreTextView = (TextView) footer.findViewById(R.id.txt_loading);
        loadingMoreTextView.setText(resources.getString(R.string.bbs_loading_more));
        footer.setVisibility(View.GONE);
        listView.getRefreshableView().addFooterView(footer);
    }

    /**
     * 设置读取下一页的信息
     *
     * @param lastId 最后一条评论的id
     */
    private void setNextPage(long lastId) {
        footer.setVisibility(View.VISIBLE);
        loadingMoreProgressBar.setVisibility(View.VISIBLE);
        loadingMoreTextView.setText(getResources().getString(R.string.bbs_loading_more));
        lastCommentId = lastId;
        commentListView.getRefreshableView().setOnScrollListener(onScrollListener);
    }

    /**
     * 设置没有更多可读取的了
     */
    private void setNoMore() {
        loadingMoreProgressBar.setVisibility(View.GONE);
        loadingMoreTextView.setText(getResources().getString(R.string.bbs_no_more_topic));
        adapter.notifyDataSetChanged();
    }

    private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            int lastInScreen = firstVisibleItem + visibleItemCount;
            if (lastInScreen == totalItemCount) {
                view.setOnScrollListener(null); //每次加载的时候都不再监听
                logger.debug("load next page: " + lastCommentId);
                loadMore(lastCommentId);
            }
        }
    };

    class MyAdapter extends BaseAdapter {

        public MyAdapter() {
            fb = FinalBitmap.create(getActivity());
        }

        @Override
        public int getCount() {
            if (list.size() == 8) {
                return list.size();
            } else if (list.size() < 8) {
                return list.size() + 1;
            } else {
                return -1;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getActivity(), R.layout.view_bbs_new_topic_emotion_thumb, null);
                holder.imgThumb = (ImageView) convertView.findViewById(R.id.emotion_thumb);
                holder.imgDelete = (ImageView) convertView.findViewById(R.id.delete_emotion);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (list.size() < 8 && position == list.size()) {
                holder.imgDelete.setVisibility(View.INVISIBLE);
                holder.imgThumb.setImageDrawable(getResources().getDrawable(R.drawable.btn_bbs_add_emotion));
                holder.imgThumb.setTag("add_img");
                holder.imgThumb.setOnClickListener(mOnClickListener);
            } else {
                holder.imgDelete.setTag(list.get(position));
                fb.display(holder.imgThumb, list.get(position), getActivity().getResources());
                holder.imgDelete.setOnClickListener(mOnClickListener);
            }

            return convertView;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }
    }


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if ("add_img".equals((String) v.getTag())) {
                //添加图片
                Intent intent = new Intent(getActivity(), SelectMorePicActivity.class);
                intent.putStringArrayListExtra(SelectMorePicActivity.IMG_URI_LIST, list);
                startActivityForResult(intent, REQUEST_CODE_ADD_EMOTION);
            } else {
                //删除这张图
                list.remove((String) v.getTag());
                refreshEmotionCountStatus();
                emotionAdapter.notifyDataSetChanged();
            }
        }
    };

    class ViewHolder {
        ImageView imgThumb;
        ImageView imgDelete;
    }
}
