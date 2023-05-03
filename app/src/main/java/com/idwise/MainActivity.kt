package com.idwise

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.idwise.sdk.IDWise
import com.idwise.sdk.IDWiseSDKCallback
import com.idwise.sdk.IDWiseSDKStepCallback
import com.idwise.sdk.data.models.IDWiseSDKError
import com.idwise.sdk.data.models.IDWiseSDKTheme
import com.idwise.sdk.data.models.JourneyInfo
import com.idwise.sdk.data.models.StepResult
import com.idwise.ui.theme.IDWISETheme

class MainActivity : ComponentActivity() {
    private val journeyDefinitionId = "f6d0fb45-036f-4cd2-b9f1-9badff97bb59"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IDWise.initialize(
            "QmFzaWMgWmpaa01HWmlORFV0TURNMlppMDBZMlF5TFdJNVpqRXRPV0poWkdabU9UZGlZalU1T20xbFlqSlZjVGRQZVd0ellXOUhVSHBUVVZKMVptbHlVbFF6U1Rob1ZqUktObU5JYmtOMGNHST0=",
            IDWiseSDKTheme.LIGHT
        ) { error: IDWiseSDKError? ->
            error?.printStackTrace()
        }
        IDWise.startDynamicJourney(
            context = this,
            journeyDefinitionId = journeyDefinitionId,
            referenceNo = null,
            locale = "en",
            journeyCallback = object : IDWiseSDKCallback {
                override fun onJourneyStarted(journeyInfo: JourneyInfo) {
                    Log.d("IDWiseSDKCallback", "onJourneyStarted")
                }

                override fun onJourneyCompleted(
                    journeyInfo: JourneyInfo,
                    isSucceeded: Boolean,
                ) {
                    Log.d("IDWiseSDKCallback", "onJourneyCompleted")
                }

                override fun onJourneyResumed(journeyInfo: JourneyInfo) {
                    Log.d("IDWiseSDKCallback", "onJourneyResumed")
                }

                override fun onJourneyCancelled(journeyInfo: JourneyInfo?) {
                    Log.d("IDWiseSDKCallback", "onJourneyCancelled")
                }


                override fun onError(error: IDWiseSDKError) {
                    Log.d("IDWiseSDKCallback", "onError ${error.message}")
                }
            },
            stepCallback = object : IDWiseSDKStepCallback {
                override fun onStepCaptured(
                    stepId: String,
                    bitmap: Bitmap?,
                    croppedBitmap: Bitmap?,
                ) {
                    //This event triggers when User has captured the image from the camera
                }

                override fun onStepResult(stepId: String, stepResult: StepResult?) {
                    //This event is triggered when Image processing is completed at the backend.
                    //stepResult contains the details of the processing output
                }

                override fun onStepConfirmed(stepId: String) {
                    Log.d("onStepConfirmed", "Step $stepId confirmed!!")
                }

            }
        )


    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IDWISETheme {
        Greeting("Android")
    }
}