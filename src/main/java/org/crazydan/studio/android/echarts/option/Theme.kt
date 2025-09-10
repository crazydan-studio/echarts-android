package org.crazydan.studio.android.echarts.option

import androidx.compose.ui.graphics.Color
import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.ThemeHolder

/**
 * 主题相关配置
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-08
 */
@EChartsOption
class Theme(
    private val holder: ThemeHolder
) {

    /** 背景色，默认无背景 */
    fun backgroundColor(value: Color) = holder.backgroundColor(value)
}