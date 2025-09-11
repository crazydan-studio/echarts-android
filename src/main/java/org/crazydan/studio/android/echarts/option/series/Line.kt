package org.crazydan.studio.android.echarts.option.series

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.option.Series
import org.crazydan.studio.android.echarts.option.SeriesHolder

/**
 * 折线/面积图
 *
 * 折线图是用折线将各个数据点标志连接起来的图表，用于展现数据的变化趋势。可用于直角坐标系和极坐标系上
 *
 * [说明文档](https://echarts.apache.org/zh/option.html#series-line)
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-10
 */
@EChartsOption
class SeriesLine(
    private val holder: SeriesLineHolder = SeriesLineHolder(),
) : Series(holder) {

    /** 是否绘制平滑曲线 */
    fun smooth(value: Boolean) {
        holder.smooth = value
    }

    /** 是否连接空数据 */
    fun connectNulls(value: Boolean) {
        holder.connectNulls = value
    }
}

class SeriesLineHolder(
    var connectNulls: Boolean = true,
    var smooth: Boolean = true,
) : SeriesHolder(
    type = Series.Type.Line,
)