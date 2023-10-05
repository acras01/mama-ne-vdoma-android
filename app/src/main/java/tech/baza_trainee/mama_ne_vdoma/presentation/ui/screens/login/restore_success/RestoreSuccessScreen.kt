package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.login.restore_success

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import tech.baza_trainee.mama_ne_vdoma.R
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.SurfaceWithNavigationBars
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.TopBarWithoutArrow
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.extensions.ButtonText

@Composable
fun RestoreSuccessScreen(
    modifier: Modifier = Modifier,
    goToMain: () -> Unit
) {
    SurfaceWithNavigationBars(
        modifier = modifier
    ) {
        ConstraintLayout(
            modifier = modifier.fillMaxWidth()
        ) {
            val (title, image, btnMain) = createRefs()

            val topGuideline = createGuidelineFromTop(0.2f)

            TopBarWithoutArrow(
                modifier = modifier
                    .fillMaxWidth()
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        bottom.linkTo(topGuideline)
                        height = Dimension.fillToConstraints
                    },
                title = "Пароль збережено",
                info = "Ви успішно змінили свій пароль. " +
                        "Будь ласка, використовуйте цей новий пароль" +
                        "при вході в додаток"
            )

            Image(
                modifier = modifier
                    .constrainAs(image) {
                        top.linkTo(topGuideline)
                        bottom.linkTo(btnMain.top, 64.dp)
                        height = Dimension.fillToConstraints
                    }
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.restore_pass_end),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )

            Button(
                modifier = modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .constrainAs(btnMain) {
                        bottom.linkTo(parent.bottom)
                    }
                    .height(48.dp),
                onClick = goToMain
            ) {
                ButtonText(
                    text = "На головну сторінку"
                )
            }
        }
    }
}

@Composable
@Preview
fun RestoreSuccessPreview() {
    RestoreSuccessScreen(
        goToMain = {}
    )
}