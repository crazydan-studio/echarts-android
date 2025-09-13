package org.crazydan.studio.android.echarts.option.coord

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable
import org.crazydan.studio.android.echarts.option.Size
import org.crazydan.studio.android.echarts.option.SizeScope

/**
 * 直角坐标系 [Grid] 中的 x 轴
 *
 * 一般情况下单个 [Grid] 组件最多只能放上下两个 x 轴，
 * 多于两个 x 轴需要通过配置 [position] 属性防止同个位置多个 x 轴的重叠
 *
 * [说明文档](https://echarts.apache.org/en/option.html#xAxis)
 *
 * @param id 坐标轴唯一标识，在有多个 x 轴时，[org.crazydan.studio.android.echarts.option.Series] 可通过该标识指定数据所属的数轴
 * @param gridId 指定坐标轴所属的 [Grid]
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
@EChartsOption
class GridXAxis(
    private val id: String?,
    private val gridId: String?,
    private val holder: GridAxisHolder = GridAxisHolder(id = id, gridId = gridId),
) : GridAxis(holder) {

    /**
     * x 轴的位置
     *
     * [Grid] 默认将第一个 x 轴放置于底部（`bottom`），第二个 x 轴则放置在第一个 x 轴的另一侧（`top`）
     */
    fun position(block: GridXAxisPosition.() -> Unit) {
        GridXAxisPosition(holder).block()
    }
}

/**
 * 直角坐标系 [Grid] 中的 y 轴
 *
 * 一般情况下单个 [Grid] 组件最多只能放左右两个 y 轴，
 * 多于两个 y 轴需要通过配置 [position] 属性防止同个位置多个 y 轴的重叠
 *
 * [说明文档](https://echarts.apache.org/en/option.html#yAxis)
 *
 * @param id 坐标轴唯一标识，在有多个 y 轴时，[org.crazydan.studio.android.echarts.option.Series] 可通过该标识指定数据所属的数轴
 * @param gridId 指定坐标轴所属的 [Grid]
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
@EChartsOption
class GridYAxis(
    private val id: String?,
    private val gridId: String?,
    private val holder: GridAxisHolder = GridAxisHolder(id = id, gridId = gridId),
) : GridAxis(holder) {

    /**
     * y 轴的位置
     *
     * [Grid] 默认将第一个 y 轴放置于左侧（`left`），第二个 y 轴则放置在第一个 y 轴的另一侧（`right`）
     */
    fun position(block: GridYAxisPosition.() -> Unit) {
        GridYAxisPosition(holder).block()
    }
}

open class GridAxis(
    private val holder: GridAxisHolder,
) : JSONable {

    enum class Type {
        Value, Category, Time, Log
    }

    enum class Position {
        Top, Bottom, Left, Right
    }

    override fun toJSON(): String = holder.toJSON()

    /** 是否显示 */
    fun show(value: Boolean) {
        holder.show = value
    }

    /** 坐标轴名称 */
    fun name(value: String, block: GridAxisName.() -> Unit) {
        holder.name = value
        GridAxisName(holder).block()
    }

    /** 坐标轴类型 */
    fun type(block: GridAxisType.() -> Unit) {
        GridAxisType(holder).block()
    }

    /** 坐标轴刻度最小值 */
    fun minValue(value: Number) {
        holder.min = value
    }

    /** 坐标轴刻度最大值 */
    fun maxValue(value: Number) {
        holder.max = value
    }

    /** [坐标轴刻度配置](https://echarts.apache.org/zh/option.html#xAxis.axisTick) */
    fun axisTick(block: GridAxisTick.() -> Unit) {
        holder.axisTick = GridAxisTick().apply(block)
    }
}

// Note: 在该 Holder 中同时定义 x 与 y 轴的全部属性
open class GridAxisHolder(
    val id: String?,
    val gridId: String?,
    var show: Boolean = true,

    var name: String? = null,
    var nameLocation: GridAxisName.Position? = null,

    var type: GridAxis.Type = GridAxis.Type.Category,
    var scale: Boolean = false,
    var min: Number? = null,
    var max: Number? = null,

    var position: GridAxis.Position? = null,
    var offset: Size? = null,

    var data: List<GridAxisData>? = null,

    var axisTick: GridAxisTick? = null,
) : JSONable

@EChartsOption
class GridAxisName(
    private val holder: GridAxisHolder
) {

    enum class Position {
        Start, Middle, End
    }

    /** 显示位置 */
    fun position(block: GridAxisNamePosition.() -> Unit) {
        GridAxisNamePosition(holder).block()
    }
}

@EChartsOption
class GridAxisNamePosition(
    private val holder: GridAxisHolder
) {

    fun start() {
        holder.nameLocation = GridAxisName.Position.Start
    }

    fun middle() {
        holder.nameLocation = GridAxisName.Position.Middle
    }

    fun end() {
        holder.nameLocation = GridAxisName.Position.End
    }
}

@EChartsOption
class GridAxisType(
    private val holder: GridAxisHolder
) {

    /** 数值轴，适用于连续数据 */
    fun value(block: (GridAxisTypeValue.() -> Unit)? = null) {
        holder.type = GridAxis.Type.Value

        block?.let {
            GridAxisTypeValue(holder).block()
        }
    }

    /** 类目轴，适用于离散的类目数据 */
    fun category(block: (GridAxisDataListByType.() -> Unit)? = null) {
        holder.type = GridAxis.Type.Category

        block?.let {
            GridAxisDataListByType(holder).block()
        }
    }

    /**
     * 时间轴，适用于连续的时序数据，与数值轴相比时间轴带有时间的格式化，
     * 在刻度计算上也有所不同，例如会根据跨度的范围来决定使用月，星期，日还是小时范围的刻度
     */
    fun time() {
        holder.type = GridAxis.Type.Time
    }

    /**
     * 对数轴。适用于对数数据。对数轴下的堆积柱状图或堆积折线图可能带来很大的视觉误差，
     * 并且在一定情况下可能存在非预期效果，应避免使用
     */
    fun log() {
        holder.type = GridAxis.Type.Log
    }
}

@EChartsOption
class GridAxisTypeValue(
    private val holder: GridAxisHolder,
) {

    /**
     * 坐标轴数值是否从 0 值开始。设置成 `true` 后坐标刻度将强制包含零刻度。
     * 在双数值轴的散点图中比较有用
     */
    fun fromZero(value: Boolean) {
        holder.scale = !value
    }
}

@EChartsOption
class GridXAxisPosition(
    private val holder: GridAxisHolder,
) : SizeScope {

    /**
     * 将 x 轴放置于 [Grid] 的顶部
     *
     * @param offset 偏移量，避免多个顶部的 x 轴重叠
     */
    fun top(offset: Size? = null) {
        updateAxisPosition(holder, GridAxis.Position.Top, offset)
    }

    /**
     * 将 x 轴放置于 [Grid] 的底部
     *
     * @param offset 偏移量，避免多个底部的 x 轴重叠
     */
    fun bottom(offset: Size? = null) {
        updateAxisPosition(holder, GridAxis.Position.Bottom, offset)
    }
}

@EChartsOption
class GridYAxisPosition(
    private val holder: GridAxisHolder,
) : SizeScope {

    /**
     * 将 y 轴放置于 [Grid] 的左侧
     *
     * @param offset 偏移量，避免多个左侧的 y 轴重叠
     */
    fun left(offset: Size? = null) {
        updateAxisPosition(holder, GridAxis.Position.Left, offset)
    }

    /**
     * 将 y 轴放置于 [Grid] 的右侧
     *
     * @param offset 偏移量，避免多个右侧的 y 轴重叠
     */
    fun right(offset: Size? = null) {
        updateAxisPosition(holder, GridAxis.Position.Right, offset)
    }
}

private fun updateAxisPosition(
    holder: GridAxisHolder,
    position: GridAxis.Position,
    offset: Size?,
) {
    holder.position = position
    holder.offset = offset
}

@EChartsOption
class GridAxisTick() : JSONable {
    private val holder = GridAxisTickHolder()

    override fun toJSON(): String = holder.toJSON()

    /** 是否显示 */
    fun show(value: Boolean) {
        holder.show = value
    }

    /** 刻度线是否与标签对齐 */
    fun alignWithLabel(value: Boolean) {
        holder.alignWithLabel = value
    }
}

data class GridAxisTickHolder(
    var show: Boolean = true,
    var alignWithLabel: Boolean? = null,
) : JSONable

@EChartsOption
class GridAxisDataListByType(
    private val holder: GridAxisHolder
) {

    /** 坐标轴标签列表 */
    fun data(block: GridAxisDataList.() -> Unit) {
        val items = mutableListOf<GridAxisData>()
        GridAxisDataList(items).block()

        holder.data = items.toList()
    }
}

@EChartsOption
class GridAxisDataList(
    private val items: MutableList<GridAxisData>
) {

    /**
     * 坐标轴标签项
     *
     * @param value 标签名
     */
    fun item(value: String, block: GridAxisData.() -> Unit) {
        val data = GridAxisDataHolder(value = value)
        items.add(
            GridAxisData(data).apply(block)
        )
    }
}

@EChartsOption
class GridAxisData(
    private val holder: GridAxisDataHolder,
) : JSONable {
    override fun toJSON(): String = holder.toJSON()

    // fun textStyle(...) {}
}

data class GridAxisDataHolder(
    var value: String,
    var textStyle: Any? = null,
) : JSONable