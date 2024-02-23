package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.main.main.notifications.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import tech.baza_trainee.mama_ne_vdoma.R
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.font_size_14_sp
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.font_size_16_sp
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.redHatDisplayFontFamily
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.size_16_dp
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.size_24_dp
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.size_8_dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcceptRequestDialog(
    groupName: String,
    onDismiss: () -> Unit
) {
    AlertDialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(size_8_dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = size_8_dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Error,
                contentDescription = "alert",
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = size_16_dp)
                    .padding(horizontal = size_16_dp),
                text = stringResource(id = R.string.join_request_accepted, groupName),
                fontSize = font_size_14_sp,
                fontFamily = redHatDisplayFontFamily,
                textAlign = TextAlign.Start
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = size_24_dp)
                    .align(Alignment.End)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onDismiss() },
                text = stringResource(id = R.string.action_close),
                fontSize = font_size_16_sp,
                fontFamily = redHatDisplayFontFamily,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}