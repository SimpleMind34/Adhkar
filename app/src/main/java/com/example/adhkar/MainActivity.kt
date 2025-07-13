package com.example.adhkar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adhkar.ui.theme.AdhkarTheme
import java.nio.file.WatchEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdhkarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AdhkarApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class DhikrItem(val textId: Int, val referenceId: Int, val initialCount: Int)

@Composable
fun Dhikr(modifier: Modifier = Modifier,
          dhikr: Int, dhikrResource: Int,
          dhikrCount: Int,
          dhikrCountOnClick: () -> Unit,
          nextDhikrOnClick: () -> Unit,
          previousDhikrOnClick: () -> Unit,
          currentDhikr: Int,
          total: Int){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Flexible content area (text)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // This takes all remaining vertical space
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(dhikr),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(dhikrResource),
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // 2. Buttons fixed at bottom
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Button(onClick = { previousDhikrOnClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = "previous saying",
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))
                Button(
                    onClick = { dhikrCountOnClick() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = dhikrCount.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))
                Button(onClick = { nextDhikrOnClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = "next saying",
                    )
                }
            }

            Text(
                text = "$currentDhikr/$total",
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

}

@Composable
fun AdhkarApp( modifier: Modifier = Modifier) {
    val sharedList = listOf(
        DhikrItem(R.string.med1, R.string.med1r, 1),
        DhikrItem(R.string.med2, R.string.med2r, 1),
        DhikrItem(R.string.med3, R.string.med3r, 1),
        DhikrItem(R.string.med4, R.string.med4r, 1),
        DhikrItem(R.string.med5, R.string.med5r, 3),
        DhikrItem(R.string.med6, R.string.med6r, 3),
        DhikrItem(R.string.med7, R.string.med7r, 10),
        DhikrItem(R.string.med8, R.string.med8r, 100)
    )

    val morningOnlyList = listOf(
        DhikrItem(R.string.md9, R.string.med9r, 1),
        DhikrItem(R.string.md10, R.string.med10r, 1),
        DhikrItem(R.string.md11, R.string.med11r, 1),
        DhikrItem(R.string.md12, R.string.md12r, 1),
        DhikrItem(R.string.md13, R.string.md13r, 4)
    )

    val eveningOnlyList = listOf(
        DhikrItem(R.string.ed9, R.string.med9r, 1),
        DhikrItem(R.string.ed10, R.string.med10r, 1),
        DhikrItem(R.string.ed11, R.string.med11r, 1),
        DhikrItem(R.string.ed12, R.string.ed12r, 1)
    )


    var currentState by remember { mutableIntStateOf(0) }
    var dhirkCount by remember { mutableIntStateOf(1) }
    var morningOrEvening by remember { mutableIntStateOf(0) }

    val dhikrList = when (morningOrEvening) {
        0 -> sharedList + morningOnlyList
        1 -> sharedList + eveningOnlyList
        else -> emptyList()
    }

    if (currentState == 0) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Centered content
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = "اختر نوع الأذكار",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1976D2), // consistent blue
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Button(
                    onClick = {
                        currentState = 1
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // green
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    Text(
                        text = "أذكار الصباح",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        morningOrEvening = 1
                        currentState = 1
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), // blue
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    Text(
                        text = "أذكار المساء",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // Bottom-aligned text
            Text(
                text = "مأخوذة من كتاب\n\"الخلاصة الحسناء في أذكار الصباح والمساء\"\nللعلامة صالح العصيمي حفظه الله",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
                modifier = Modifier.padding(12.dp),
                lineHeight = 24.sp,
                textAlign = TextAlign.Center
            )
        }

    } else {
        val index = currentState - 1
        if (index < dhikrList.size) {
            val item = dhikrList[index]
                    Dhikr(
                        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
                        dhikr = item.textId,
                        dhikrResource = item.referenceId,
                        dhikrCount = dhirkCount,
                        dhikrCountOnClick = {
                            dhirkCount--
                            if (dhirkCount == 0) {
                                dhirkCount =
                                    if (index + 1 < dhikrList.size) dhikrList[index + 1].initialCount else 1
                                currentState++
                                if (index + 1 >= dhikrList.size) {
                                    currentState = 0
                                    morningOrEvening = 0
                                }
                            }
                        },
                        nextDhikrOnClick = {
                            if (index + 1 < dhikrList.size) {
                                dhirkCount = dhikrList[index + 1].initialCount
                                currentState++
                            } else {
                                currentState = 0
                                morningOrEvening = 0
                            }
                        },
                        previousDhikrOnClick = {
                            if (index > 0) {
                                dhirkCount = dhikrList[index - 1].initialCount
                                currentState--
                            } else {
                                currentState = 0
                            }
                        }, currentDhikr = index + 1, total = dhikrList.size
                    )
                }
            }
        }







@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AdhkarTheme {
        AdhkarApp()
    }
}