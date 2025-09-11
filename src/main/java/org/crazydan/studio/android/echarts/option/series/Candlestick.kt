package org.crazydan.studio.android.echarts.option.series

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.option.Series
import org.crazydan.studio.android.echarts.option.SeriesHolder

/**
 * K 线图
 *
 * [说明文档](https://echarts.apache.org/zh/option.html#series-candlestick)
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-10
 */
@EChartsOption
class SeriesCandlestick(
    private val holder: SeriesCandlestickHolder = SeriesCandlestickHolder(),
) : Series(holder) {
}

class SeriesCandlestickHolder(

) : SeriesHolder(
    type = Series.Type.Candlestick,
)