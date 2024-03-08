package tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import tech.baza_trainee.mama_ne_vdoma.R
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.font_size_14_sp
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.redHatDisplayFontFamily
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.size_16_dp
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.size_8_dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressNotCheckedDialog(
    title: String,
    onDismiss: () -> Unit = {}
) {
    BasicAlertDialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(size_8_dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(size_16_dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                modifier = Modifier
                    .padding(top = size_16_dp)
                    .padding(horizontal = size_16_dp)
                    .fillMaxWidth(),
                text = title,
                fontSize = font_size_14_sp,
                textAlign = TextAlign.Start,
                fontFamily = redHatDisplayFontFamily
            )

            Text(
                text = stringResource(id = R.string.got_it),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.End)
                    .clickable {
                        onDismiss()
                    }
                    .padding(size_16_dp)
            )
        }
    }
}