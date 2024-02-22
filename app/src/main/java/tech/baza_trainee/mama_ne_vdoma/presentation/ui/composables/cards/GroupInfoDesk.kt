package tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.cards

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import tech.baza_trainee.mama_ne_vdoma.R
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.custom_views.ButtonText
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.custom_views.Rating
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.dialogs.MakeAdminConfirmationDialog
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.composables.dialogs.MakeAdminDialog
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.main.model.GroupUiModel
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.screens.main.model.MemberUiModel
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.LogoutButtonColor
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.LogoutButtonTextColor
import tech.baza_trainee.mama_ne_vdoma.presentation.ui.theme.redHatDisplayFontFamily

@Composable
fun GroupInfoDesk(
    modifier: Modifier = Modifier,
    group: GroupUiModel = GroupUiModel(
        adminId = "0",
        members = List(10) {
            MemberUiModel(name = "Name $it", id = "$it")
        }
    ),
    currentUserId: String = "",
    onEdit: (String) -> Unit = {},
    onSelect: (String) -> Unit = {},
    onLeave: (String) -> Unit = {},
    onSwitchAdmin: (String, String) -> Unit = {_,_->},
    onDelete: (String) -> Unit = {},
    onRateUser: (String) -> Unit = {}
) {
    val isAdmin  = currentUserId == group.adminId
    val isMyGroup = group.members.map { it.id }.contains(currentUserId)

    var showAdminDialog by rememberSaveable { mutableStateOf(false) }
    var showAdminConfirmationDialog by rememberSaveable { mutableStateOf(false) }
    var adminDialogData by rememberSaveable { mutableStateOf(Triple("", "", "")) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(all = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(4.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(group.avatar)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.no_photo),
                contentDescription = "group_avatar",
                contentScale = ContentScale.FillWidth
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isAdmin) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(28.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .width(16.dp)
                                .height(28.dp),
                            painter = painterResource(id = R.drawable.ic_crown),
                            contentDescription = "admin"
                        )
                        Text(
                            text = stringResource(id = R.string.you_are_admin),
                            fontSize = 14.sp,
                            fontFamily = redHatDisplayFontFamily,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Rating(
                    modifier = Modifier.padding(all = 8.dp),
                    rating = group.rating
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.format_group_id, group.id),
            fontSize = 11.sp,
            fontFamily = redHatDisplayFontFamily
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = group.name,
            fontSize = 16.sp,
            fontFamily = redHatDisplayFontFamily,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                modifier = Modifier
                    .padding(end = 4.dp),
                painter = painterResource(id = R.drawable.ic_group_children),
                contentDescription = "children_age",
                contentScale = ContentScale.Inside
            )

            Text(
                text = "${group.ages} р.",
                fontSize = 14.sp,
                fontFamily = redHatDisplayFontFamily
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        var toggleMoreInfo by rememberSaveable { mutableStateOf(false) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            group.members.forEachIndexed { index, member ->
                if (member.id != currentUserId) {
                    if (index < 3) {
                        AsyncImage(
                            modifier = Modifier
                                .padding(end = 2.dp)
                                .height(24.dp)
                                .width(24.dp)
                                .clip(CircleShape),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(member.avatar)
                                .placeholder(R.drawable.ic_user_no_photo)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(id = R.drawable.ic_user_no_photo),
                            fallback = painterResource(id = R.drawable.ic_user_no_photo),
                            contentDescription = "member",
                            contentScale = ContentScale.Fit
                        )
                    } else return@forEachIndexed
                }
            }

            if (group.members.size > 3)
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+${group.members.size - 3}",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 10.sp,
                        fontFamily = redHatDisplayFontFamily
                    )
                }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { toggleMoreInfo = !toggleMoreInfo }
            ) {
                Icon(
                    painter = painterResource(
                        id = if (toggleMoreInfo)
                            R.drawable.outline_arrow_drop_up_24
                        else
                            R.drawable.outline_arrow_drop_down_24
                    ),
                    contentDescription = "toggle_more",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (toggleMoreInfo) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        modifier = Modifier.padding(end = 4.dp),
                        painter = painterResource(id = R.drawable.ic_group_location),
                        contentDescription = "group_location",
                        contentScale = ContentScale.Inside
                    )

                    Text(
                        text = group.address,
                        fontSize = 14.sp,
                        fontFamily = redHatDisplayFontFamily
                    )
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    text = group.description,
                    fontSize = 11.sp,
                    fontFamily = redHatDisplayFontFamily
                )

                group.members.sortedBy { it.name }.forEach {
                    if (it.id != currentUserId) {
                        MemberContent(
                            member = it,
                            isMyGroup = isMyGroup,
                            isAdmin = isAdmin,
                            onRateUser = onRateUser
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }

            ScheduleInfoDesk(
                modifier = Modifier.padding(top = 16.dp),
                schedule = group.schedule,
                dayText = stringResource(id = R.string.child_care_days_set),
                periodText = stringResource(id = R.string.child_care_hours_set)
            )

            if (isAdmin) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(48.dp),
                    onClick = { onEdit(group.id) }
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 4.dp),
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "edit_group"
                    )
                    ButtonText(
                        text = stringResource(id = R.string.edit_group)
                    )
                }

                if (group.members.map { it.id }.filterNot { it == group.adminId }.isNotEmpty()) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(48.dp),
                        onClick = { showAdminDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        border = BorderStroke(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_crown),
                            contentDescription = "make_admin",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        ButtonText(
                            text = stringResource(id = R.string.make_admin)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = { onDelete(group.id) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LogoutButtonColor,
                        contentColor = LogoutButtonTextColor
                    )
                ) {
                    ButtonText(
                        text = stringResource(id = R.string.delete_group)
                    )
                }
            }
        }

        if (isMyGroup && !isAdmin) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = { onLeave(group.id) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LogoutButtonColor,
                    contentColor = LogoutButtonTextColor
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "exit"
                )
                Spacer(modifier = Modifier.width(4.dp))
                ButtonText(
                    text = stringResource(id = R.string.leave_group)
                )
            }
        } else if (!isAdmin) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = 4.dp),
                    text = stringResource(id = R.string.join_group),
                    fontSize = 11.sp,
                    fontFamily = redHatDisplayFontFamily
                )

                Spacer(modifier = Modifier.weight(1f))

                Checkbox(
                    checked = group.isChecked,
                    onCheckedChange = { onSelect(group.id) }
                )
            }
        }

        if (showAdminDialog) {
            MakeAdminDialog(
                group = group,
                onDismiss = { showAdminDialog = false },
                onMakeAdmin = { selectedId, selectedName ->
                    adminDialogData = Triple(group.id, selectedId, selectedName)
                    showAdminConfirmationDialog = true
                }
            )
        }

        if (showAdminConfirmationDialog) {
            MakeAdminConfirmationDialog(
                memberName = adminDialogData.third,
                onDismiss = { showAdminConfirmationDialog = false },
                onMakeAdmin = {
                    showAdminConfirmationDialog = false
                    showAdminDialog = false
                    onSwitchAdmin(adminDialogData.first, adminDialogData.second)
                }
            )
        }
    }
}

@Composable
private fun MemberContent(
    member: MemberUiModel,
    isMyGroup: Boolean,
    isAdmin: Boolean,
    onRateUser: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(end = 8.dp)
                .height(24.dp)
                .width(24.dp)
                .clip(CircleShape),
            model = ImageRequest.Builder(LocalContext.current)
                .data(member.avatar)
                .placeholder(R.drawable.ic_user_no_photo)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.ic_user_no_photo),
            fallback = painterResource(id = R.drawable.ic_user_no_photo),
            contentDescription = "member",
            contentScale = ContentScale.Fit
        )
        Text(
            text = member.name,
            fontSize = 14.sp,
            fontFamily = redHatDisplayFontFamily
        )

        Spacer(modifier = Modifier.weight(1f))

        Rating(rating = member.rating) { onRateUser(member.id) }

        if (isMyGroup || isAdmin) {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()

            IconButton(
                onClick = {
                    scope.launch {
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${member.email}"))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        ContextCompat.startActivity(context, intent, null)
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mail),
                    contentDescription = "email",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                onClick = {
                    scope.launch {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${member.phone}"))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        ContextCompat.startActivity(context, intent, null)
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_phone),
                    contentDescription = "phone",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Composable
fun GroupIfoDeskPreview() {
    GroupInfoDesk(
        group = GroupUiModel(
            adminId = "0",
            members = List(10) {
                MemberUiModel(name = "Name $it", id = "$it")
            }
        ),
        currentUserId = "0",
        onEdit = {},
        onSelect = {},
        onLeave = {},
        onSwitchAdmin = { _, _ -> },
        onDelete = {},
        onRateUser = {}
    )
}
