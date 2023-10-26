package com.example.horsechallenge.payPremium.ui

import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.horsechallenge.horseGame.ui.model.ItemModel
import com.example.horsechallenge.ui.theme.md_theme_box_selected
import com.example.horsechallenge.ui.theme.md_theme_box_selected_bf
import com.example.horsechallenge.ui.theme.md_theme_light_onSecondary
import com.example.horsechallenge.ui.theme.md_theme_light_secondary
import com.example.horsechallenge.ui.theme.md_theme_light_tertiary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayPremiumViewModel @Inject constructor(
) : ViewModel() {

    private var showLabel = false

    fun homeConstraints(): ConstraintSet {
        return ConstraintSet {

            val topGuideRef = createRefFor("topGuideRef")
            val titleRef = createRefFor("titleRef")
            val noAdsRef = createRefFor("noAdsRef")
            val unlimitedLivesRef = createRefFor("unlimitedLivesRef")
            val keepLvlRef = createRefFor("keepLvlRef")
            val payRef = createRefFor("payRef")
            val bottomGuideRef = createRefFor("bottomGuideRef")

            constrain(topGuideRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(titleRef.top)
            }
            constrain(titleRef) {
                top.linkTo(topGuideRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(noAdsRef.top)
            }
            constrain(noAdsRef) {
                top.linkTo(titleRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(unlimitedLivesRef.top)
            }
            constrain(unlimitedLivesRef) {
                top.linkTo(noAdsRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(keepLvlRef.top)
            }
            constrain(keepLvlRef) {
                top.linkTo(unlimitedLivesRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(payRef.top)
            }
            constrain(payRef) {
                top.linkTo(keepLvlRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottomGuideRef.top)
            }
            constrain(bottomGuideRef) {
                top.linkTo(payRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }

            createVerticalChain(
                topGuideRef,
                titleRef,
                noAdsRef,
                unlimitedLivesRef,
                keepLvlRef,
                payRef,
                bottomGuideRef,
                chainStyle = ChainStyle.SpreadInside
            )
        }
    }

}