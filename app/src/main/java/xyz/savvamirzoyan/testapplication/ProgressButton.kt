package xyz.savvamirzoyan.testapplication

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import xyz.savvamirzoyan.testapplication.databinding.LayoutProgressButtonBinding


class ProgressButton : FrameLayout {

    private lateinit var binding: LayoutProgressButtonBinding

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val view = inflateView(context)
        setUpBinding(view)
        attrs?.let { applyAttributes(context, it) }
    }

    private fun inflateView(context: Context): View =
        LayoutInflater.from(context).inflate(R.layout.layout_progress_button, this)

    private fun setUpBinding(view: View) {
        binding = LayoutProgressButtonBinding.bind(view)
    }

    private fun applyAttributes(context: Context, attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ProgressButton, 0, 0).apply {
            try {
                setButtonText(getString(R.styleable.ProgressButton_text) ?: "")
                setButtonIsEnabled(getBoolean(R.styleable.ProgressButton_isEnabled, true))

                val isLoading = getBoolean(R.styleable.ProgressButton_isLoading, false)
                if (isLoading) startLoading() else stopLoading()
            } finally {
                recycle()
            }
        }
    }

    fun setButtonText(text: String) {
        binding.button.text = text
        invalidate()
        requestLayout()
    }

    fun setButtonIsEnabled(isEnabled: Boolean) {
        binding.button.isEnabled = isEnabled
        invalidate()
        requestLayout()
    }

    fun startLoading() {
        binding.button.visibility = View.INVISIBLE
        binding.progressCircular.visibility = View.VISIBLE
        invalidate()
        requestLayout()
    }

    fun stopLoading() {
        binding.button.visibility = View.VISIBLE
        binding.progressCircular.visibility = View.GONE
        invalidate()
        requestLayout()
    }

    fun setOnClickListener(function: () -> Unit) {
        binding.button.setOnClickListener { function() }
    }
}