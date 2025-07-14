package com.example.adhkar

import android.annotation.SuppressLint
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
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
                Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(16.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(dhikr),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            lineHeight = 36.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(dhikrResource),
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 32.sp
                        )
                    }

                }
            }
        }


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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
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


    var currentState by rememberSaveable { mutableIntStateOf(0) }
    var dhirkCount by rememberSaveable { mutableIntStateOf(1) }
    var morningOrEvening by rememberSaveable { mutableIntStateOf(0) }

    val dhikrList = when (morningOrEvening) {
        0 -> sharedList + morningOnlyList
        1 -> sharedList + eveningOnlyList
        else -> emptyList()
    }

    if (currentState == 0) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = " {وَاذْكُرِ ٱسْمَ رَبِّكَ بُكْرَةً وَأَصِيلًا} " ,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFB8860B),
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp,
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .fillMaxWidth()
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
        Scaffold(
            topBar = {
                if (currentState != 0) {
                    androidx.compose.material3.TopAppBar(
                        title = { Text("الرجوع") },
                        navigationIcon = {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                                contentDescription = "رجوع",
                                modifier = Modifier
                                    .clickable {
                                        currentState = 0
                                        morningOrEvening = 0
                                        dhirkCount = 1
                                    }
                                    .padding(16.dp)
                            )
                        }
                    )
                }
            }
        ) { innerPadding ->
            // Main content goes here
            Box(modifier = Modifier.padding(innerPadding)) {
                // your Dhikr composable, etc.
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
                            } else if (dhikrList.isNotEmpty()) {
                                currentState = 0
                                morningOrEvening = 0
                                dhirkCount = dhikrList[0].initialCount
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


            }
        }

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AdhkarTheme {
        AdhkarApp()
    }
}
