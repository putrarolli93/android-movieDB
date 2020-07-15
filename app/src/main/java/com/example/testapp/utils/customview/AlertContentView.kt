package com.example.testapp.utils.customview

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.example.testapp.R
import kotlinx.android.synthetic.main.layout_alert.view.*

class AlertContentView : LinearLayout {

    private var onRetry: ((alertContentView: AlertContentView?) -> Unit?)? = null

    val textViewTitleAlert: TextView by lazy {
        tvTitleAlert
    }

    val textViewBodyAlert: TextView by lazy {
        tvBodyAlert
    }

    val imageViewAlert: ImageView by lazy {
        ivLogoAlert
    }

    val buttonAlert: Button by lazy {
        btnAlert
    }

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        val activity = (context as Activity)
        val view = LayoutInflater.from(context).inflate(R.layout.layout_alert, null, false)
        val linearLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        linearLayoutParams.topMargin = activity.getActionBarSizeDimension() + activity.getStatusBarDimension()
        addView(view, linearLayoutParams)
    }

    fun setTitleMessage(title: String) {
        tvTitleAlert.text = title
    }

    fun setBodyMessage(title: String) {
        tvBodyAlert.text = title
    }

    fun setIconAlertDrawable(@DrawableRes iconAlertDialog: Int) {
        ivLogoAlert.setDrawableVectorCompat(iconAlertDialog)
    }

    fun setButtonActionText(actionText: String) {
        btnAlert.text = actionText
        btnAlert.goneIf(false)
        btnAlert.setOnClickListener {
            onRetry?.invoke(this)
            gone()
        }
    }

    fun setOnRetryListener(onRetry: ((alertContentView: AlertContentView?) -> Unit?)?) {
        this.onRetry = onRetry
    }

    fun gone() {
        goneIf(true)
    }

    fun visible() {
        goneIf(false)
    }

    fun Activity.getActionBarSizeDimension(): Int {
        val ta = theme.obtainStyledAttributes(
            intArrayOf(android.R.attr.actionBarSize))
        return ta.getDimension(0, 0f).toInt()
    }

    fun Activity.getStatusBarDimension(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun ImageView.setDrawableVectorCompat(@DrawableRes drawableId: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            this.setImageDrawable(
                VectorDrawableCompat.create(this.context.resources, drawableId, this.context.theme)
            )
        } else {
            this.setImageResource(drawableId)
        }
    }

    fun View.goneIf(condition: Boolean) {
        if (condition) this.visibility = View.GONE else this.visibility = View.VISIBLE
    }

}