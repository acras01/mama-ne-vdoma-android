package tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.login.email_confirm

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import tech.baza_trainee.mama_ne_vdoma.R
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.SurfaceWithNavigationBars
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.TopBarWithoutArrow
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.getTextWithUnderline
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.redHatDisplayFontFamily
import tech.baza_trainee.mama_ne_vdoma.presentation.utils.extensions.ButtonText

@Composable
fun EmailConfirmScreen(
    modifier: Modifier = Modifier,
    email: String = "",
    onLogin: () -> Unit = {},
    onSendAgain: () -> Unit = {}
) {
    SurfaceWithNavigationBars(
        modifier = modifier
    ) {
        ConstraintLayout(
            modifier = modifier.fillMaxWidth()
        ) {
            val (title, image, btnStart, btnLogin) = createRefs()

            val topGuideline = createGuidelineFromTop(0.2f)

            TopBarWithoutArrow(
                modifier = modifier
                    .fillMaxWidth()
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        bottom.linkTo(topGuideline)
                        height = Dimension.fillToConstraints
                    },
                title = "Лист був відправлений",
                info = "Перевірте свою пошту $email, " +
                        "щоб отримати подальші інструкції з " +
                        "відновлення паролю"
            )

            Image(
                modifier = modifier
                    .constrainAs(image) {
                        top.linkTo(topGuideline)
                        bottom.linkTo(btnStart.top, 64.dp)
                        height = Dimension.fillToConstraints
                    }
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.email_confirm),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )

            Button(
                modifier = modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .fillMaxWidth()
                    .constrainAs(btnStart) {
                        bottom.linkTo(btnLogin.top)
                    }
                    .height(48.dp),
                onClick = { onLogin() }
            ) {
                ButtonText(
                    text = "Увійти"
                )
            }

            Text(
                text = getTextWithUnderline("Не отримали листа? ", "Відправити ще раз"),
                modifier = modifier
                    .constrainAs(btnLogin) {
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onSendAgain()
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                textAlign = TextAlign.Center,
                fontFamily = redHatDisplayFontFamily
            )
        }
    }
}

@Composable
@Preview
fun EmailConfirmPreview() {
    EmailConfirmScreen()
}