package vandy.mooc;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A main Activity that prompts the user for a URL to an image and then uses
 * Intents and other Activities to download the image and view it.
 */
public class MainActivity extends LifecycleLoggingActivity {
	/**
	 * Debugging tag used by the Android logger.
	 */
	private final String TAG = getClass().getSimpleName();

	/**
	 * Intent Static String
	 */
	public final static String PATHIMAGENAME = "PathToImageName";
	public final static String URINAME = "URL_NAME";

	/**
	 * A value that uniquely identifies the request to download an image.
	 */
	private static final int DOWNLOAD_IMAGE_REQUEST = 1;

	/**
	 * EditText field for entering the desired URL to an image.
	 */
	private EditText mUrlEditText;

	/**
	 * Download Image Button
	 */
	private Button downloadButton;

	/**
	 * URL for the image that's downloaded by default if the user doesn't
	 * specify otherwise.
	 */
	private Uri mDefaultUrl = Uri
			.parse("http://www.dre.vanderbilt.edu/~schmidt/robot.png");

	/**
	 * Hook method called when a new instance of Activity is created. One time
	 * initialization code goes here, e.g., UI layout and some class scope
	 * variable initialization.
	 *
	 * @param savedInstanceState
	 *            object that contains saved state information.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Always call super class for necessary
		// initialization/implementation.
		// @@ TODO -- you fill in here.
		super.onCreate(savedInstanceState);

		// Set the default layout.
		// @@ TODO -- you fill in here.
		setContentView(R.layout.main_activity);

		// Cache the EditText that holds the urls entered by the user
		// (if any).
		// @@ TODO -- you fill in here.
		mUrlEditText = (EditText) findViewById(R.id.url);
/*		String text = mUrlEditText.getText().toString();
		if (null != text)
			mDefaultUrl = Uri.parse(text);*/

		downloadButton = (Button) findViewById(R.id.button1);
		downloadButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDefaultUrl = getUrl();
				downloadImage(v);
			}
		});

	}

	/**
	 * Called by the Android Activity framework when the user clicks the
	 * "Download Image" button.
	 *
	 * @param view
	 *            The view.
	 */
	public void downloadImage(View view) {
		try {
			// Hide the keyboard.
			hideKeyboard(this, mUrlEditText.getWindowToken());

			// Call the makeDownloadImageIntent() factory method to
			// create a new Intent to an Activity that can download an
			// image from the URL given by the user. In this case
			// it's an Intent that's implemented by the
			// DownloadImageActivity.
			// @@ TODO - you fill in here.
			Intent intent = makeDownloadImageIntent(mDefaultUrl);

			// Start the Activity associated with the Intent, which
			// will download the image and then return the Uri for the
			// downloaded image file via the onActivityResult() hook
			// method.
			// @@ TODO -- you fill in here.
			startActivityForResult(intent, DOWNLOAD_IMAGE_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Hook method called back by the Android Activity framework when an
	 * Activity that's been launched exits, giving the requestCode it was
	 * started with, the resultCode it returned, and any additional data from
	 * it.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check if the started Activity completed successfully.
		// @@ TODO -- you fill in here, replacing true with the right
		// code.
		if (RESULT_OK == resultCode) {
			// Check if the request code is what we're expecting.
			// @@ TODO -- you fill in here, replacing true with the
			// right code.
			if (DOWNLOAD_IMAGE_REQUEST == requestCode) {
				// Call the makeGalleryIntent() factory method to
				// create an Intent that will launch the "Gallery" app
				// by passing in the path to the downloaded image
				// file.
				// @@ TODO -- you fill in here.
				String pathToImageFile = data.getStringExtra(PATHIMAGENAME);
				Intent intent = makeGalleryIntent(pathToImageFile);
				// Start the Gallery Activity.
				// @@ TODO -- you fill in here.
				startActivity(intent);
			}
		}
		// Check if the started Activity did not complete successfully
		// and inform the user a problem occurred when trying to
		// download contents at the given URL.
		// @@ TODO -- you fill in here, replacing true with the right
		// code.
		else {
			Toast.makeText(this, "The download did not complete successfully!",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Factory method that returns an implicit Intent for viewing the downloaded
	 * image in the Gallery app.
	 */
	private Intent makeGalleryIntent(String pathToImageFile) {
		// Create an intent that will start the Gallery app to view
		// the image.
		// TODO -- you fill in here, replacing "null" with the proper
		// code.
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);  
		Uri imgUri = Uri.fromFile(new File(pathToImageFile));  
		intent.setDataAndType(imgUri, "image/*");  
		return intent;
	}

	/**
	 * Factory method that returns an implicit Intent for downloading an image.
	 */
	private Intent makeDownloadImageIntent(Uri url) {
		// Create an intent that will download the image from the web.
		// TODO -- you fill in here, replacing "null" with the proper
		// code.
		Intent intent = new Intent(this, DownloadImageActivity.class);
		intent.putExtra(URINAME, url);
		return intent;
	}

	/**
	 * Get the URL to download based on user input.
	 */
	protected Uri getUrl() {
		Uri url = null;

		// Get the text the user typed in the edit text (if anything).
		url = Uri.parse(mUrlEditText.getText().toString());

		// If the user didn't provide a URL then use the default.
		String uri = url.toString();
		if (uri == null || uri.equals(""))
			url = mDefaultUrl;

		// Do a sanity check to ensure the URL is valid, popping up a
		// toast if the URL is invalid.
		// @@ TODO -- you fill in here, replacing "true" with the
		// proper code.
		if (URLUtil.isValidUrl(url.toString()) )
			return url;
		else {
			Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show();
			return null;
		}
	}

	/**
	 * This method is used to hide a keyboard after a user has finished typing
	 * the url.
	 */
	public void hideKeyboard(Activity activity, IBinder windowToken) {
		InputMethodManager mgr = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(windowToken, 0);
	}
}
