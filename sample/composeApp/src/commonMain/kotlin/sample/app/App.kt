package sample.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yaikostudio.kparsetron.Kparsetron
import com.yaikostudio.kparsetron.entities.ParsedVideoDetail
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun App() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        var input by remember { mutableStateOf("") }
        BasicTextField(
            value = input,
            onValueChange = { input = it },
            modifier = Modifier.background(Color.Gray).padding(8.dp).fillMaxWidth(),
        )

        var foundVideo by remember { mutableStateOf<ParsedVideoDetail?>(null) }
        BasicText(
            text = "Search for: $input",
            modifier = Modifier
                .background(Color.Blue)
                .clickable {
                    GlobalScope.launch {
                        val result = Kparsetron().parse(input)
                        foundVideo = result as? ParsedVideoDetail
                    }
                }
                .background(Color.Red),
        )

        BasicTextField(
            value = foundVideo?.data?.title ?: "No video found",
            onValueChange = {},
            modifier = Modifier.background(Color.Gray).fillMaxSize(0.5f),
        )
    }
}
