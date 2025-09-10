package org.crazydan.studio.android.echarts.option

import androidx.annotation.IntRange
import org.crazydan.studio.android.echarts.JSONable
import kotlin.math.roundToInt

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-10
 */
data class Color(
    val type: Type,
    val value: String? = null,
) : JSONable {

    enum class Type {
        Rgba, Linear, Radial
    }

    override fun toJSON(): String =
        when (type) {
            Type.Rgba -> "\"$value\""
            Type.Linear -> TODO()
            Type.Radial -> TODO()
        }
}

interface ColorScope {

    fun rgba(value: Long, alpha: Float = 1f): Color =
        rgba(
            r = ((value shr 16) and 0xFF).toInt(),
            g = ((value shr 8) and 0xFF).toInt(),
            b = (value and 0xFF).toInt(),
            alpha = alpha,
        )

    fun rgba(
        @IntRange(from = 0, to = 0xFF) r: Int,
        @IntRange(from = 0, to = 0xFF) g: Int,
        @IntRange(from = 0, to = 0xFF) b: Int,
        alpha: Float = 1f
    ): Color =
        rgba(
            androidx.compose.ui.graphics.Color(
                red = r, green = g, blue = b,
                alpha = (alpha * 0xFF).roundToInt(),
            )
        )

    fun rgba(value: androidx.compose.ui.graphics.Color): Color =
        Color(type = Color.Type.Rgba, value = toRgba(value))
}

fun toRgba(color: androidx.compose.ui.graphics.Color): String {
    val r = (color.red * 0xFF).roundToInt()
    val g = (color.green * 0xFF).roundToInt()
    val b = (color.blue * 0xFF).roundToInt()
    val a = color.alpha

    return "rgba($r,$g,$b,$a)"
}