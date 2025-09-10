package org.crazydan.studio.android.echarts.option

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
) : ColorScope {

    /** 背景色，默认无背景 */
    fun backgroundColor(value: Color) = holder.backgroundColor(value)

    /** [Series] 颜色列表。如果 [Series] 没有设置颜色，则会依次循环从该列表中取颜色作为系列颜色 */
    fun seriesColors(value: List<Color>) = holder.seriesColors(value)
}