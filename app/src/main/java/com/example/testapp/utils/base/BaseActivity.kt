package com.example.testapp.utils.base

import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.R
import com.example.testapp.utils.customview.AlertContentView
import com.example.testapp.utils.customview.CustomStateView
import com.example.testapp.utils.customview.HorizontalProgressBar
import com.google.android.material.snackbar.Snackbar

interface BaseView {

    fun getStateView(): CustomStateView?

    fun loadingData(isFromSwipe: Boolean = false)

}

abstract class BaseActivity : AppCompatActivity(), BaseView {

    protected var horizontalProgressBar: HorizontalProgressBar? = null
    private var alertContentView: AlertContentView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindowTransition()
        showHorizonTopProgressBar()
        supportActionBar?.hide()
    }

    private fun setupWindowTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            with(window) {
                requestFeature(
                    Window.FEATURE_CONTENT_TRANSITIONS
                            or Window.FEATURE_ACTIVITY_TRANSITIONS)
                enterTransition = Fade()
                exitTransition = Fade()
            }
        }
    }

    fun showHorizonTopProgressBar() {
        if (horizontalProgressBar == null) {
            horizontalProgressBar = HorizontalProgressBar(this)
            decorViewGroup()?.addView(
                horizontalProgressBar,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        horizontalProgressBar?.visible()
    }

    fun goneHorizonTopProgressBar() {
        horizontalProgressBar?.gone()
    }

    fun decorViewGroup(): ViewGroup? {
        return when (isDecorViewGroup()) {
            true -> window.decorView as ViewGroup
            else -> null
        }
    }

    fun isDecorViewGroup(): Boolean {
        return window.decorView is ViewGroup
    }

    fun showContentServerError(
        messageAlerts: Pair<String, String>?, textAlertButton: String?,
        iconDrawableAlert: Int?,
        onRetry: ((alertContentView: AlertContentView?) -> Unit)?
    ) {
        if (!isDecorViewGroup())
            return
        addAlertContentView()
        setContentAlertMessages(Pair(getString(R.string.error_server_busy),
            getString(R.string.error_general_issue)), messageAlerts)
        setContentAlertDrawable(R.drawable.ic_server_error, iconDrawableAlert)
        setActionButtonText(getString(R.string.text_try_again), textAlertButton)

        alertContentView?.let {
            with(it) {
                setOnRetryListener(onRetry)
                visible()
            }
        }
    }

    fun goneContentServerError(onRetry: ((alertContentView: AlertContentView?) -> Unit)?) {
        alertContentView?.goneIf(true)
    }

    private fun setActionButtonText(
        defaultTextAlertButton: String,
        textAlertButton: String?
    ) {
        when (textAlertButton) {
            null -> alertContentView?.setButtonActionText(defaultTextAlertButton)
            else -> alertContentView?.setButtonActionText(textAlertButton)
        }
    }

    private fun setContentAlertDrawable(
        @DrawableRes defaultIconDrawableAlert: Int,
        @DrawableRes iconDrawableAlert: Int?
    ) {
        when (iconDrawableAlert) {
            null -> alertContentView?.setIconAlertDrawable(defaultIconDrawableAlert)
            else -> alertContentView?.setIconAlertDrawable(iconDrawableAlert)
        }
    }

    private fun setContentAlertMessages(
        defaultMessages: Pair<String, String>,
        messageAlerts: Pair<String, String>?
    ) {
        when (messageAlerts) {
            null -> {
                with(defaultMessages) {
                    alertContentView?.let {
                        it.setTitleMessage(first)
                        it.setBodyMessage(second)
                    }
                }
            }
            else -> {
                with(messageAlerts) {
                    alertContentView?.let {
                        it.setTitleMessage(first)
                        it.setBodyMessage(second)
                    }
                }
            }
        }
    }

    private fun addAlertContentView() {
        when (alertContentView) {
            null -> {
                alertContentView = instanceAlertContentView()
                decorViewGroup()?.addView(alertContentView,
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            }
        }
    }

    private fun instanceAlertContentView(): AlertContentView {
        return AlertContentView(this)
    }

    fun View.goneIf(condition: Boolean) {
        if (condition) this.visibility = View.GONE else this.visibility = View.VISIBLE
    }

    fun onDataNotFound() {
        when (val sv = getStateView()) {
            null -> {
                displayMessage(getString(R.string.error_no_data))
            }
            else -> {

                if (sv.isDataLoaded) {
                    displayMessage(getString(R.string.error_no_data))
                    return
                }

                val data = dataStateView {
                    icon = R.drawable.ic_img_no_internet
                    title = getString(R.string.error_no_data_title)
                    description = getString(R.string.error_no_data_desc)
                }
                sv.setDataStateView(data)
                sv.visible()
            }
        }
    }

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun dataStateView(inputs: CustomStateView.DataStateView.() -> Unit): CustomStateView.DataStateView
            = CustomStateView.DataStateView().apply(inputs)

    override fun getStateView(): CustomStateView? = null

    private fun displayMessage(message: String?) {
        getParentViewGroup()?.let { vg ->
            message?.let { Snackbar.make(vg, it, Snackbar.LENGTH_SHORT).show() }
        }
    }

    fun getParentViewGroup(): ViewGroup? = decorViewGroup()

    override fun loadingData(isFromSwipe: Boolean) {
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

}