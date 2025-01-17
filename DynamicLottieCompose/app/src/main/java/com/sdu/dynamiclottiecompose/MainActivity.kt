package com.sdu.dynamiclottiecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.sdu.dynamiclottiecompose.ui.theme.DynamicLottieComposeTheme

private val LEMON_GREEN = Color(0x0002d11b)
private val LEMON_RED = Color(0x00780000)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DynamicLottieComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,

                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Cyan)
                            .padding(48.dp),
                    ) {
                        var accessoriesColor by remember { mutableStateOf(LEMON_GREEN) }
                        var showMouth by remember { mutableStateOf(true) }
                        Text(
                            text = "Dynamic Properties Example",
                            modifier = Modifier.padding(innerPadding)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        LemonLottieImage(
                            color = accessoriesColor,
                            showMouth = showMouth,
                            modifier = Modifier
                                .width(300.dp)
                                .height(300.dp)
                        )
                        Button(
                            onClick = { accessoriesColor = LEMON_RED },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                                .height(48.dp)
                        ) {
                            Text(text = "Accessories Red")
                        }
                        Button(
                            onClick = { accessoriesColor = LEMON_GREEN },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                                .height(48.dp)
                        ) {
                            Text(text = "Accessories Green")
                        }
                        Button(
                            onClick = { showMouth = false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                                .height(48.dp)
                        ) {
                            Text(text = "Act cool")
                        }
                        Button(
                            onClick = { showMouth = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                                .height(48.dp)
                        ) {
                            Text(text = "Act Happy")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LemonLottieImage(
    color: Color,
    showMouth: Boolean,
    modifier: Modifier = Modifier,
) {
    val lottieSpec = LottieCompositionSpec.Url("https://drive.google.com/uc?id=1ebWqd_e2ci4kSKB83e37q2Bl0YMadwxv")
    val composition by rememberLottieComposition(
        spec = lottieSpec,
        cacheKey = System.currentTimeMillis().toString(),
    )
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            keyPath = arrayOf(
                "lemon", "small-leaf", "**"
            ),
            value = color.toArgb(),
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            keyPath = arrayOf(
//                "lemon", "big-leaf", "**"
                // This is an example of refencing the full keypath
                // whereas elsewhere we use the Globstar ** shortcut
                "lemon", "big-leaf", "Big_leaf_00000052817416241482779350000003325489541389591972_", "Fill 1"
            ),
            value = color.toArgb(),
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            keyPath = arrayOf(
                "lemon", "lemon-body-sunglasses", "**"
            ),
            value = color.toArgb(),
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.OPACITY,
            keyPath = arrayOf(
                "lemon", "mouth", "**"
            ),
            value = if (showMouth) 100 else 0,
        )
    )
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        enableMergePaths = true,
        dynamicProperties = dynamicProperties,
        modifier = modifier,
    )
    // This is the code used to resolve the keypaths
    // the log was saved in resolved-keypaths.txt
    // you need the appcompat dependency for this to work
//    AndroidView(
//        factory = { c ->
//            LottieAnimationView(c).apply {
//                setAnimationFromUrl("https://drive.google.com/uc?id=1ebWqd_e2ci4kSKB83e37q2Bl0YMadwxv")
//                addLottieOnCompositionLoadedListener {
//                    Log.d("TUT", "Keypaths: ${resolveKeyPath(KeyPath("**"))}")
//                }
//            }
//        }
//    )
}