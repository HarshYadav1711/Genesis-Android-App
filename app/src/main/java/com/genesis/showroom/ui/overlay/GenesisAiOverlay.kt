package com.genesis.showroom.ui.overlay

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.genesis.showroom.data.ChatMessage
import com.genesis.showroom.data.ChatRole
import com.genesis.showroom.data.MockChatResponses
import com.genesis.showroom.data.Vehicle
import com.genesis.showroom.data.VoiceState
import com.genesis.showroom.ui.language.GenesisLanguage
import com.genesis.showroom.ui.language.GenesisLanguageProvider
import com.genesis.showroom.ui.theme.GenesisBlack
import com.genesis.showroom.ui.theme.GenesisBorder
import com.genesis.showroom.ui.theme.GenesisCharcoal
import com.genesis.showroom.ui.theme.GenesisCopper
import com.genesis.showroom.ui.theme.GenesisFontFamily
import com.genesis.showroom.ui.theme.GenesisMuted
import com.genesis.showroom.ui.theme.GenesisPanel
import com.genesis.showroom.ui.theme.GenesisScrim
import com.genesis.showroom.ui.theme.GenesisSilver
import com.genesis.showroom.ui.theme.GenesisTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GenesisAiOverlay(
    isOpen: Boolean,
    onClose: () -> Unit,
    currentVehicle: Vehicle?,
    modifier: Modifier = Modifier,
) {
    val isArabic = GenesisLanguage.isArabic
    val language = GenesisLanguage.current
    val layoutDirection = LocalLayoutDirection.current
    val scope = rememberCoroutineScope()

    var voiceState by remember { mutableStateOf(VoiceState.IDLE) }
    var textInput by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<ChatMessage>() }
    var hasGreeted by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    fun resetConversation() {
        messages.clear()
        textInput = ""
        voiceState = VoiceState.IDLE
        hasGreeted = false
    }

    fun handleClose() {
        resetConversation()
        onClose()
    }

    suspend fun simulateSpeaking() {
        voiceState = VoiceState.SPEAKING
        delay(1800)
        voiceState = VoiceState.IDLE
    }

    suspend fun simulateMockResponse(userText: String) {
        voiceState = VoiceState.THINKING
        delay(1200)
        val response = MockChatResponses.respond(language, userText, currentVehicle)
        messages.add(ChatMessage(role = ChatRole.ASSISTANT, content = response))
        simulateSpeaking()
    }

    LaunchedEffect(isOpen) {
        if (!isOpen) {
            resetConversation()
            return@LaunchedEffect
        }
        if (hasGreeted) return@LaunchedEffect
        hasGreeted = true

        val greeting = MockChatResponses.greeting(language, currentVehicle)
        messages.add(ChatMessage(role = ChatRole.ASSISTANT, content = greeting))
        simulateSpeaking()
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    val slideOffset = if (layoutDirection == LayoutDirection.Rtl) -1 else 1

    Box(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isOpen,
            enter = fadeIn(tween(200)),
            exit = fadeOut(tween(200)),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GenesisScrim)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = ::handleClose,
                    ),
            )
        }

        AnimatedVisibility(
            visible = isOpen,
            enter = slideInHorizontally(
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = 280f),
                initialOffsetX = { it * slideOffset },
            ) + fadeIn(tween(200)),
            exit = slideOutHorizontally(
                animationSpec = tween(250),
                targetOffsetX = { it * slideOffset },
            ) + fadeOut(tween(200)),
            modifier = Modifier.align(
                if (layoutDirection == LayoutDirection.Rtl) Alignment.CenterStart else Alignment.CenterEnd,
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(min = 320.dp, max = 480.dp)
                    .fillMaxWidth(0.92f)
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .imePadding()
                    .background(GenesisCharcoal)
                    .border(
                        width = GenesisTheme.spacing.borderWidthSubtle,
                        color = GenesisBorder,
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {},
                    ),
            ) {
                OverlayHeader(
                    voiceState = voiceState,
                    isArabic = isArabic,
                    onClose = ::handleClose,
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(GenesisBlack.copy(alpha = 0.3f))
                        .border(
                            width = GenesisTheme.spacing.borderWidthSubtle,
                            color = GenesisBorder,
                        )
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    AudioVisualizer(state = voiceState)
                }

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(messages, key = { it.content + it.role.name }) { message ->
                        ChatBubble(message = message, isArabic = isArabic)
                    }

                    if (voiceState == VoiceState.THINKING) {
                        item(key = "thinking") {
                            ThinkingBubble()
                        }
                    }
                }

                if (currentVehicle != null) {
                    VehicleContextBar(
                        vehicleName = currentVehicle.name,
                        isArabic = isArabic,
                    )
                }

                OverlayInputArea(
                    textInput = textInput,
                    onTextChange = { textInput = it },
                    voiceState = voiceState,
                    isArabic = isArabic,
                    onMicClick = {
                        when (voiceState) {
                            VoiceState.IDLE -> {
                                voiceState = VoiceState.LISTENING
                                scope.launch {
                                    delay(2000)
                                    if (voiceState == VoiceState.LISTENING) {
                                        voiceState = VoiceState.THINKING
                                        delay(1200)
                                        messages.add(
                                            ChatMessage(
                                                role = ChatRole.USER,
                                                content = if (isArabic) "أخبرني عن هذه السيارة" else "Tell me about this vehicle",
                                            ),
                                        )
                                        simulateMockResponse(
                                            if (isArabic) "أخبرني عن هذه السيارة" else "Tell me about this vehicle",
                                        )
                                    }
                                }
                            }
                            VoiceState.LISTENING -> voiceState = VoiceState.IDLE
                            VoiceState.SPEAKING -> voiceState = VoiceState.LISTENING
                            VoiceState.THINKING -> Unit
                        }
                    },
                    onSend = {
                        val text = textInput.trim()
                        if (text.isBlank() || voiceState == VoiceState.THINKING) return@OverlayInputArea
                        textInput = ""
                        messages.add(ChatMessage(role = ChatRole.USER, content = text))
                        scope.launch { simulateMockResponse(text) }
                    },
                    onEndConversation = ::handleClose,
                )
            }
        }
    }
}

@Composable
private fun OverlayHeader(
    voiceState: VoiceState,
    isArabic: Boolean,
    onClose: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = GenesisTheme.spacing.borderWidthSubtle,
                color = GenesisBorder,
            )
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(GenesisCopper.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Mic,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = GenesisCopper,
                )
            }
            Column {
                Text(
                    text = "Genesis AI",
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = GenesisSilver,
                )
                Text(
                    text = statusLabel(voiceState, isArabic),
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = GenesisMuted,
                )
            }
        }

        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = if (isArabic) "إغلاق" else "Close",
                modifier = Modifier.size(14.dp),
                tint = GenesisMuted,
            )
        }
    }
}

@Composable
private fun ChatBubble(
    message: ChatMessage,
    isArabic: Boolean,
) {
    val isUser = message.role == ChatRole.USER
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isUser) 16.dp else 4.dp,
                        bottomEnd = if (isUser) 4.dp else 16.dp,
                    ),
                )
                .background(
                    when {
                        message.isError -> Color(0xFF7F1D1D).copy(alpha = 0.2f)
                        isUser -> GenesisCopper.copy(alpha = 0.2f)
                        else -> GenesisPanel
                    },
                )
                .border(
                    width = GenesisTheme.spacing.borderWidthSubtle,
                    color = when {
                        message.isError -> Color(0xFFB91C1C).copy(alpha = 0.3f)
                        isUser -> GenesisCopper.copy(alpha = 0.3f)
                        else -> GenesisBorder
                    },
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isUser) 16.dp else 4.dp,
                        bottomEnd = if (isUser) 4.dp else 16.dp,
                    ),
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            if (!isUser && !message.isError) {
                Text(
                    text = "GENESIS AI",
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp,
                    letterSpacing = 1.5.sp,
                    color = GenesisCopper,
                    modifier = Modifier.padding(bottom = 6.dp),
                )
            }
            Text(
                text = message.content,
                fontFamily = GenesisFontFamily.Body,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = when {
                    message.isError -> Color(0xFFFCA5A5)
                    else -> GenesisSilver
                },
            )
        }
    }
}

@Composable
private fun ThinkingBubble() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        Row(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 16.dp,
                    ),
                )
                .background(GenesisPanel)
                .border(
                    width = GenesisTheme.spacing.borderWidthSubtle,
                    color = GenesisBorder,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 16.dp,
                    ),
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(3) { index ->
                ThinkingDot(delayMillis = index * 150)
            }
        }
    }
}

@Composable
private fun ThinkingDot(delayMillis: Int) {
    val infiniteTransition = androidx.compose.animation.core.rememberInfiniteTransition(label = "bubbleDot")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -5f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
            animation = tween(durationMillis = 600, delayMillis = delayMillis),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse,
        ),
        label = "dotY",
    )
    Box(
        modifier = Modifier
            .size(6.dp)
            .graphicsLayer { translationY = offsetY }
            .clip(RoundedCornerShape(percent = 50))
            .background(GenesisCopper),
    )
}

@Composable
private fun VehicleContextBar(
    vehicleName: String,
    isArabic: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(GenesisBlack.copy(alpha = 0.4f))
            .border(
                width = GenesisTheme.spacing.borderWidthSubtle,
                color = GenesisBorder,
            )
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = null,
            modifier = Modifier.size(12.dp),
            tint = GenesisCopper,
        )
        Text(
            text = buildString {
                append(if (isArabic) "تتصفح " else "Currently viewing: ")
            },
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = GenesisMuted,
        )
        Text(
            text = vehicleName,
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = GenesisCopper,
        )
    }
}

@Composable
private fun OverlayInputArea(
    textInput: String,
    onTextChange: (String) -> Unit,
    voiceState: VoiceState,
    isArabic: Boolean,
    onMicClick: () -> Unit,
    onSend: () -> Unit,
    onEndConversation: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = GenesisTheme.spacing.borderWidthSubtle,
                color = GenesisBorder,
            )
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            MicButton(
                voiceState = voiceState,
                onClick = onMicClick,
            )
        }

        Text(
            text = if (isArabic) "أو اكتب سؤالك" else "or type your question",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 12.dp),
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            letterSpacing = 1.5.sp,
            color = GenesisMuted,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                value = textInput,
                onValueChange = onTextChange,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(GenesisBlack)
                    .border(
                        width = GenesisTheme.spacing.borderWidthSubtle,
                        color = GenesisBorder,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontFamily = GenesisFontFamily.Body,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = GenesisSilver,
                ),
                cursorBrush = SolidColor(GenesisCopper),
                enabled = voiceState != VoiceState.THINKING,
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box {
                        if (textInput.isEmpty()) {
                            Text(
                                text = if (isArabic) {
                                    "اسأل عن أي سيارة Genesis..."
                                } else {
                                    "Ask about any Genesis vehicle..."
                                },
                                fontFamily = GenesisFontFamily.Body,
                                fontSize = 14.sp,
                                color = GenesisMuted,
                            )
                        }
                        innerTextField()
                    }
                },
            )

            IconButton(
                onClick = onSend,
                enabled = textInput.isNotBlank() && voiceState != VoiceState.THINKING,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (textInput.isNotBlank() && voiceState != VoiceState.THINKING) {
                            GenesisCopper
                        } else {
                            GenesisCopper.copy(alpha = 0.4f)
                        },
                    )
                    .size(44.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = if (isArabic) "إرسال" else "Send",
                    tint = GenesisBlack,
                )
            }
        }

        Text(
            text = if (isArabic) "إنهاء المحادثة" else "End Conversation",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .clickable(onClick = onEndConversation)
                .padding(vertical = 4.dp),
            fontFamily = GenesisFontFamily.Body,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            letterSpacing = 1.5.sp,
            color = GenesisMuted,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
    }
}

@Composable
private fun MicButton(
    voiceState: VoiceState,
    onClick: () -> Unit,
) {
    val isListening = voiceState == VoiceState.LISTENING
    val isSpeaking = voiceState == VoiceState.SPEAKING
    val isThinking = voiceState == VoiceState.THINKING

    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(percent = 50))
            .background(
                when {
                    isListening -> Color.White
                    isSpeaking -> GenesisCopper
                    else -> GenesisCopper.copy(alpha = 0.2f)
                },
            )
            .border(
                width = if (isListening || isSpeaking) 0.dp else 2.dp,
                color = GenesisCopper,
                shape = RoundedCornerShape(percent = 50),
            )
            .clickable(enabled = !isThinking, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        when {
            isListening -> {
                Icon(
                    imageVector = Icons.Filled.Stop,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = GenesisBlack,
                )
            }
            isSpeaking -> {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = GenesisBlack,
                )
            }
            else -> {
                Icon(
                    imageVector = Icons.Filled.Mic,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = GenesisCopper,
                )
            }
        }
    }
}

private fun statusLabel(state: VoiceState, isArabic: Boolean): String = when (state) {
    VoiceState.LISTENING -> if (isArabic) "يستمع..." else "Listening..."
    VoiceState.THINKING -> if (isArabic) "يفكر..." else "Thinking..."
    VoiceState.SPEAKING -> if (isArabic) "يتحدث..." else "Speaking..."
    VoiceState.IDLE -> if (isArabic) "جاهز للمساعدة" else "Ready to assist"
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A0A, widthDp = 480, heightDp = 800)
@Composable
private fun GenesisAiOverlayPreview() {
    GenesisTheme {
        GenesisLanguageProvider {
            GenesisAiOverlay(
                isOpen = true,
                onClose = {},
                currentVehicle = Vehicle(
                    id = "g70",
                    name = "Genesis G70",
                    type = "Compact Luxury Sedan",
                    tagline = "Pure driving exhilaration, refined.",
                    image = "",
                    startingPrice = "AED 165,000",
                ),
            )
        }
    }
}
