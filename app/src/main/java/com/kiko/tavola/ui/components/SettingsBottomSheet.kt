package com.kiko.tavola.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
    useDynamicColor: Boolean,
    onUseDynamicColorChange: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = "Настройки",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
            )

            ListItem(
                headlineContent = { Text("Цвета Monet (Material You)") },
                supportingContent = { Text("Доступно на Android 12+. Можно отключить для стабильной палитры.") },
                trailingContent = {
                    Switch(
                        checked = useDynamicColor,
                        onCheckedChange = onUseDynamicColorChange
                    )
                }
            )
        }
    }
}


