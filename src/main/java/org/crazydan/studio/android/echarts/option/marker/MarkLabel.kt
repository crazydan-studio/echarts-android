package org.crazydan.studio.android.echarts.option.marker

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.option.Label
import org.crazydan.studio.android.echarts.option.LabelHolder

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-12
 */
@EChartsOption
class MarkLineLabel(
    private val holder: MarkLineLabelHolder = MarkLineLabelHolder()
) : Label(holder) {

    enum class Position {
        Start, Middle, End,
        InsideStartTop, InsideStartBottom, InsideMiddleTop,
        InsideMiddleBottom, InsideEndTop, InsideEndBottom,
    }

    /** [标签位置](https://echarts.apache.org/zh/option.html#series-line.markLine.label.position) */
    fun position(block: MarkLineLabelScope.() -> Position) {
        holder.position = MarkLineLabelScope().block()
    }
}

@EChartsOption
class MarkPointLabel(
    private val holder: MarkPointLabelHolder = MarkPointLabelHolder()
) : Label(holder) {

    enum class Position {
        Top, Left, Right, Bottom,
        Inside, InsideLeft, InsideRight,
        InsideTop, InsideBottom, InsideTopLeft,
        InsideBottomLeft, InsideTopRight, InsideBottomRight
    }

    /** [标签位置](https://echarts.apache.org/examples/zh/view.html?c=doc-example/label-position) */
    fun position(block: MarkPointLabelScope.() -> Position) {
        holder.position = MarkPointLabelScope().block()
    }
}

class MarkLineLabelHolder(
    var position: MarkLineLabel.Position? = null,
) : LabelHolder()

class MarkLineLabelScope {
    val start = MarkLineLabel.Position.Start
    val middle = MarkLineLabel.Position.Middle
    val end = MarkLineLabel.Position.End
    val insideStartTop = MarkLineLabel.Position.InsideStartTop
    val insideMiddleTop = MarkLineLabel.Position.InsideMiddleTop
    val insideMiddleBottom = MarkLineLabel.Position.InsideMiddleBottom
    val insideEndTop = MarkLineLabel.Position.InsideEndTop
    val insideEndBottom = MarkLineLabel.Position.InsideEndBottom
    val insideStartBottom = MarkLineLabel.Position.InsideStartBottom
}

class MarkPointLabelHolder(
    var position: MarkPointLabel.Position? = null,
) : LabelHolder()

class MarkPointLabelScope {
    val top = MarkPointLabel.Position.Top
    val bottom = MarkPointLabel.Position.Bottom
    val left = MarkPointLabel.Position.Left
    val right = MarkPointLabel.Position.Right
    val inside = MarkPointLabel.Position.Inside
    val insideLeft = MarkPointLabel.Position.InsideLeft
    val insideRight = MarkPointLabel.Position.InsideRight
    val insideTop = MarkPointLabel.Position.InsideTop
    val insideBottom = MarkPointLabel.Position.InsideBottom
    val insideTopLeft = MarkPointLabel.Position.InsideTopLeft
    val insideBottomLeft = MarkPointLabel.Position.InsideBottomLeft
    val insideTopRight = MarkPointLabel.Position.InsideTopRight
    val insideBottomRight = MarkPointLabel.Position.InsideBottomRight
}