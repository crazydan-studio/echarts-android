package org.crazydan.studio.android.echarts.option.series

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.option.Series
import org.crazydan.studio.android.echarts.option.SeriesHolder

/**
 * 散点（气泡）图
 *
 * [说明文档](https://echarts.apache.org/zh/option.html#series-scatter)
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-15
 */
@EChartsOption
class SeriesScatter(
    private val holder: SeriesScatterHolder = SeriesScatterHolder(),
) : Series(holder) {
    //
}

class SeriesScatterHolder(
) : SeriesHolder(
    type = Series.Type.Scatter,
)