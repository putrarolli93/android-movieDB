package com.example.testapp.utils.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.testapp.R
import kotlinx.android.synthetic.main.custom_state_view.view.*

class CustomStateView : LinearLayout {

    val iconStateView by lazy { ivStateViewIcon }

    val titleStateView by lazy { tvStateViewTitle }

    val descriptionStateView by lazy { tvStateViewDescription }

    val buttonStateView by lazy { btStateViewAction }

    // Check if data has been success loaded or not
    var isDataLoaded = false

    constructor(context: Context) : super(context) {
        setupView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setupView()
    }

    fun setDataStateView(dataStateView: DataStateView) {

        dataStateView.icon?.let {
            iconStateView?.visible()
            iconStateView?.loadFromResource(it)
        }

        dataStateView.title?.let {
            titleStateView?.visible()
            titleStateView?.text = it
        }

        dataStateView.description?.let {
            descriptionStateView?.visible()
            descriptionStateView?.text = it
        }

        dataStateView.buttonLabel?.let {
            buttonStateView?.visible()
            buttonStateView?.text = it
        }
    }

    fun setStateViewAction(stateAction: () -> Unit) {
        buttonStateView?.setOnClickListener { stateAction.invoke() }
    }

    private fun setupView() {

        val linearLayoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        )

        val view = LayoutInflater.from(context)
                .inflate(R.layout.custom_state_view, this, false)

        addView(view, linearLayoutParams)
    }

    data class DataStateView(
            var icon: Int? = null,
            var title: String? = null,
            var description: String? = null,
            var buttonLabel: String? = null,
            var onClick: () -> Unit = {}
    )

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun ImageView.loadFromResource(@DrawableRes imageRes: Int) {
        val options = RequestOptions()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .priority(Priority.IMMEDIATE)
        Glide.with(this.context)
            .load(imageRes)
            .apply(options)
            .into(this)
    }
}