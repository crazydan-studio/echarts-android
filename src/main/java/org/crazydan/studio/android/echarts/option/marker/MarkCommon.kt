package org.crazydan.studio.android.echarts.option.marker

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable
import org.crazydan.studio.android.echarts.option.Label
import org.crazydan.studio.android.echarts.option.SeriesItemStyle
import org.crazydan.studio.android.echarts.option.SeriesLineStyle

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-12
 */
@EChartsOption
open class MarkWithSymbol(
    private val holder: MarkWithSymbolHolder,
) : JSONable {

    enum class Shape {
        Circle, Rect, RoundRect, Triangle, Diamond, Pin, Arrow, None
    }

    override fun toJSON(): String = holder.toJSON()

    fun symbol(block: MarkSymbol.() -> Unit) {
        MarkSymbol(holder).block()
    }
}

@EChartsOption
open class MarkWithNameSymbol(
    private val holder: MarkWithNameSymbolHolder,
) : MarkWithSymbol(holder) {

    /** 名字 */
    fun name(value: String) {
        holder.name = value
    }
}

open class MarkWithLabelHolder(
    var label: Label? = null,

    var itemStyle: SeriesItemStyle? = null,
    var lineStyle: SeriesLineStyle? = null,
) : JSONable

open class MarkWithSymbolHolder(
    var symbol: MarkWithSymbol.Shape? = null,
    var symbolSize: Int? = null,
    var symbolRotate: Number? = null,
) : MarkWithLabelHolder()

open class MarkWithNameSymbolHolder(
    var name: String? = null,
) : MarkWithSymbolHolder()

@EChartsOption
class MarkSymbol(
    private val holder: MarkWithSymbolHolder,
) {

    /** 标记的形状 */
    fun shape(block: MarkSymbolShapeScope.() -> MarkWithSymbol.Shape) {
        holder.symbol = MarkSymbolShapeScope().block()
    }

    /** 标记的大小 */
    fun size(value: Int? = null) {
        holder.symbolSize = value
    }

    /** 标记的旋转角度：正值表示逆时针旋转 */
    fun rotate(value: Number) {
        holder.symbolRotate = value
    }
}

class MarkSymbolShapeScope {
    val circle = MarkWithSymbol.Shape.Circle
    val rect = MarkWithSymbol.Shape.Rect
    val roundRect = MarkWithSymbol.Shape.RoundRect
    val triangle = MarkWithSymbol.Shape.Triangle
    val diamond = MarkWithSymbol.Shape.Diamond
    val pin = MarkWithSymbol.Shape.Pin
    val arrow = MarkWithSymbol.Shape.Arrow
    val none = MarkWithSymbol.Shape.None
}

@EChartsOption
open class MarkDataPoint(
    private val holder: MarkDataPointHolder,
) : JSONable {

    enum class Type {
        Min, Max, Average, Median
    }

    override fun toJSON(): String = holder.toJSON()

    /** 数据的维度配置 */
    fun byDimension(block: MarkDataPointDimension.() -> Unit) {
        if (holder.coord != null) {
            throw IllegalArgumentException("the point is already defined by coordinate")
        }

        MarkDataPointDimension(holder).block()
    }

    /**
     * 数据点的坐标配置
     *
     * @param x 数据点在 x 轴上的序号或数据值
     * @param y 数据点在 y 轴上的序号或数据值
     */
    fun byCoordinate(x: Number, y: Number) {
        if (holder.type != null) {
            throw IllegalArgumentException("the point is already defined by dimension")
        }

        holder.coord = listOf(x, y)
    }
}

@EChartsOption
open class MarkDataPointWithSymbol(
    private val holder: MarkDataPointHolder,
) : MarkDataPoint(holder) {

    fun symbol(block: MarkSymbol.() -> Unit) {
        MarkSymbol(holder).block()
    }
}

open class MarkDataPointHolder(
    var type: MarkDataPoint.Type? = null,
    var valueDim: String? = null,

    var coord: List<Number>? = null,

    var xAxis: Any? = null,
    var yAxis: Any? = null,
) : MarkWithNameSymbolHolder()

@EChartsOption
class MarkDataPointDimension(
    private val holder: MarkDataPointHolder,
) {

    /** 取指定数据维度 [dimension] 中的最小值 */
    fun min(dimension: String) {
        holder.type = MarkDataPoint.Type.Min
        holder.valueDim = dimension
    }

    /** 取指定数据维度 [dimension] 中的最大值 */
    fun max(dimension: String) {
        holder.type = MarkDataPoint.Type.Max
        holder.valueDim = dimension
    }

    /** 取指定数据维度 [dimension] 中的平均值 */
    fun average(dimension: String) {
        holder.type = MarkDataPoint.Type.Average
        holder.valueDim = dimension
    }

    /** 取指定数据维度 [dimension] 中的中位数 */
    fun median(dimension: String) {
        holder.type = MarkDataPoint.Type.Median
        holder.valueDim = dimension
    }
}