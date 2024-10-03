package ru.gorbulevsv.kotlinstate

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.sharp.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import org.w3c.dom.Text
import ru.gorbulevsv.kotlinstate.R.color.purple_500
import ru.gorbulevsv.kotlinstate.texts.text2
import ru.gorbulevsv.kotlinstate.ui.theme.KotlinStateTheme

class MainActivity : ComponentActivity() {
    var size = mutableStateOf(22)
    val lineHeight = mutableStateOf(24)
    //var textOld = mutableStateOf(text2)
    var text = mutableStateOf(Html.fromHtml(text2, HtmlCompat.FROM_HTML_MODE_LEGACY).toString())
    var isBottomPanelShow = mutableStateOf(false)

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            Scaffold(topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Тексты")
                    }, actions = {
                        IconButton(onClick = { isBottomPanelShow.value = true }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "Настройка"
                            )
                        }
                    }
                )
            }) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .verticalScroll(
                            rememberScrollState()
                        )
                ) {
                    Text(
                        text = text.value,
                        fontSize = size.value.sp,
                        modifier = Modifier.padding(10.dp),
                        style = TextStyle.Default.copy(
                            lineBreak = LineBreak.Paragraph,
                            hyphens = Hyphens.Auto,
                            lineHeight = lineHeight.value.sp
                        )
                    )
                }

                PartialBottomSheet(isBottomPanelShow, size, lineHeight)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet(
    showBottomSheet: MutableState<Boolean>,
    size: MutableState<Int>,
    lineHeight: MutableState<Int>
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (showBottomSheet.value) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet.value = false }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Button(onClick = { size.value -= 1 }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Sharp.KeyboardArrowLeft,
                                contentDescription = "Уменьшить"
                            )
                        }
                        Text(
                            text = "Размер шрифта: ${size.value} sp"
                        )
                        Button(onClick = { size.value += 1 }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Sharp.KeyboardArrowRight,
                                contentDescription = "Увеличить"
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Button(onClick = { lineHeight.value -= 1 }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Sharp.KeyboardArrowLeft,
                                contentDescription = "Уменьшить"
                            )
                        }
                        Text(
                            text = "Интервал строк: ${lineHeight.value} sp"
                        )
                        Button(onClick = { lineHeight.value += 1 }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Sharp.KeyboardArrowRight,
                                contentDescription = "Увеличить"
                            )
                        }
                    }
                }
            }
        }
    }
}
