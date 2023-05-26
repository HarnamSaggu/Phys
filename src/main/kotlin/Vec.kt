import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

class Vec(var x: Double, var y: Double) {
	operator fun plus(o: Vec): Vec {
		return Vec(x + o.x, y + o.y)
	}

	operator fun times(o: Double): Vec {
		return Vec(x * o, y * o)
	}

	operator fun div(o: Double): Vec {
		return Vec(x / o, y / o)
	}

	operator fun minus(o: Vec): Vec {
		return this + -o
	}

	operator fun unaryMinus(): Vec {
		return Vec(-x, -y)
	}

	fun mag(): Double {
		return sqrt(x * x + y * y)
	}

	fun Vec.dist(a: Vec, b: Vec): Double {
		return sqrt((a.x - b.x).pow(2) + (a.y - b.y).pow(2))
	}

	fun angleBetween(o: Vec): Double {
		return acos(dot(o) / (mag() * o.mag()))
	}

	fun dot(o: Vec): Double {
		return x * o.x + y * o.y
	}

	fun setMag(o: Double): Vec {
		return this * (o / mag())
	}

	fun norm(): Vec {
		return setMag(1.0)
	}

	override fun toString(): String {
		return "($x, $y)"
	}

	override fun equals(other: Any?): Boolean {
		return other is Vec && x == other.x && y == other.y
	}

	override fun hashCode(): Int {
		return super.hashCode()
	}
}
