package com.wulan.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wulan.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    val artList = listOf(
        ArtPiece("The Scream", "Edvard Munch", 1893, R.drawable.art01, 1),
        ArtPiece("My Little Vase", "Byron", 2016, R.drawable.art02, 2),
        ArtPiece("The Arnolfini Portrait", "Jan van Eyck", 1434, R.drawable.art03, 3)
    )
    var currentIndex by remember { mutableStateOf(0) }
    val currentArt = artList[currentIndex]

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount > 0) {
                        if (currentIndex > 0) currentIndex-- else currentIndex = artList.size - 1
                    } else {
                        if (currentIndex < artList.size - 1) currentIndex++ else currentIndex = 0
                    }
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(85.dp))

            Card(
                modifier = Modifier
                    .width(268.dp)
                    .height(350.dp)
                    .shadow(8.dp, RectangleShape),
                shape = RectangleShape,
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Image(
                    painter = painterResource(id = currentArt.imageResource),
                    contentDescription = currentArt.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .width(300.dp)
                    .padding(16.dp),
                shape = RectangleShape,
                colors = CardDefaults.cardColors(containerColor = Color(0xFFECEBF4))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = currentArt.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    val artistAndYear = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                            append("${currentArt.artist} ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append("(${currentArt.year})")
                        }
                    }

                    Text(
                        text = artistAndYear,
                        fontSize = 14.sp,
                        color = Color(0xFF666666)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        currentIndex = if (currentIndex > 0) currentIndex - 1 else artList.size - 1
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF495D92)),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text("Previous", color = Color.White)
                }

                Button(
                    onClick = {
                        currentIndex = if (currentIndex < artList.size - 1) currentIndex + 1 else 0
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF495D92)),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.width(120.dp)
                ) {
                    Text("Next", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

data class ArtPiece(
    val title: String,
    val artist: String,
    val year: Int,
    val imageResource: Int,
    val id: Int
)

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceApp()
}
