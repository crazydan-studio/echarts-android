package org.crazydan.studio.android.echarts

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-05
 */
@Composable
fun ECharts(
    options: EChartsOptions,
    modifier: Modifier = Modifier,
    useDarkTheme: Boolean = false,
) {
    var isChartReady by remember { mutableStateOf(false) }

    AndroidView(
        modifier = modifier,
//        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            EChartsView(ctx, useDarkTheme).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
                listener = EChartsViewListener(
                    onChartReady = { isChartReady = true },
                )
            }
        },
        // Note: 视图重组时会调用该接口
        update = { view ->
            if (isChartReady) {
                view.updateChartOptions(options)
            }
        },
    )
}

@SuppressLint("ViewConstructor")
private class EChartsView(
    context: Context,
    useDarkTheme: Boolean,
) : WebView(context) {
    var listener: EChartsViewListener? = null

    init {
        initConfiguration()
        initWebViewClient(useDarkTheme)
    }

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    private fun initConfiguration() {
        if (isInEditMode) return

        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = false
        settings.allowContentAccess = false
        settings.blockNetworkLoads = true
        settings.blockNetworkImage = true
        settings.builtInZoomControls = false
        settings.javaScriptCanOpenWindowsAutomatically = false
        settings.allowFileAccessFromFileURLs = false
        settings.allowUniversalAccessFromFileURLs = false

        setWebContentsDebuggingEnabled(true)
        setBackgroundColor(Color.TRANSPARENT)

        // 向 js 传递当前对象并命名，在 js 中通过 window.<objName>
        // 可调用该对象中 @JavascriptInterface 标注的方法
        addJavascriptInterface(this, "__ChartWrapper__")
    }

    private fun initWebViewClient(useDarkTheme: Boolean) {
        loadUrl("file:///android_asset/echarts/index.html")

        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                safeEvaluateJavaScript("initChart($useDarkTheme)")
            }
        }
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<< js 回调函数
    @JavascriptInterface
    fun onChartReady() {
        listener?.onChartReady?.invoke()
    }

    @JavascriptInterface
    fun onChartRendered() {
        listener?.onChartRendered?.invoke()
    }
    // >>>>>>>>>>>>>>>>>>>>>>>>> js 回调函数

    // <<<<<<<<<<<<<<<<<<<<<<<<< 图表更新函数
    fun updateChartOptions(options: EChartsOptions) {
        val json = options.toJSON().replace("'", "\\'")

        safeEvaluateJavaScript("updateChartOptions('$json')")
    }
    // >>>>>>>>>>>>>>>>>>>>>>>>> 图表更新函数

    private fun safeEvaluateJavaScript(js: String) {
        if (isInEditMode) return

        evaluateJavascript("javascript:$js") {
        }
    }
}

private class EChartsViewListener(
    /** 图表已就绪，可以调用回调、更新配置、更新数据等 */
    val onChartReady: (() -> Unit)? = null,

    /** 图表数据已渲染完毕：动画等可能还在继续 */
    val onChartRendered: (() -> Unit)? = null,
)