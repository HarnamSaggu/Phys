import java.awt.BorderLayout
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingWorker
import javax.swing.WindowConstants
import kotlin.math.cos

fun main() {
	Sim()
}

class Sim : JPanel() {
	val frame: JFrame

	val a: Ball
	val b: Ball

	init {
		frame = JFrame("Phys")
		frame.setSize(200, 200)
		frame.layout = BorderLayout(0, 0)
		frame.defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
		frame.isResizable = false
		frame.add(this)
		frame.setLocationRelativeTo(null)
		frame.isVisible = true

		a = Ball(width * Math.random(), height * Math.random(), 20.0, 10.0)
		b = Ball(width * Math.random(), height * Math.random(), 20.0, 10.0)
		a.vel = Vec(Math.random() - 0.5, Math.random() - 0.5).setMag(15.0 * Math.random())
		b.vel = Vec(Math.random() - 0.5, Math.random() - 0.5).setMag(15.0 * Math.random())

		val runner = object : SwingWorker<Any?, Any?>() {
			@Throws(Exception::class)
			override fun doInBackground(): Any? {
				val run = true
				while (run) {
					Thread.sleep(10)
					repaint()
				}
				return null
			}
		}.execute()
	}

	override fun paintComponent(g: Graphics?) {
		super.paintComponent(g)
		val g2d = g as Graphics2D

		with(g2d) {
			color = Color.BLACK
			fillRect(0, 0, width, height)

			a.draw(this)
			b.draw(this)

			a.move()
			b.move()

			val aDist = b.loc - a.loc
			if (aDist.mag() <= a.radius + b.radius) {
				val aAngle = a.vel.angleBetween(aDist)
				val aImage = aDist.setMag(a.vel.mag() * cos(aAngle))

				val bDist = -aDist
				val bAngle = b.vel.angleBetween(bDist)
				val bImage = bDist.setMag(b.vel.mag() * cos(bAngle))

				val aPerp = a.vel - aImage
				val bPerp = b.vel - bImage

				a.vel = aPerp + bImage
				b.vel = bPerp + aImage
			}
		}
	}

	fun Ball.move() {
		loc += vel

		if (loc.x - radius <= 0 || loc.x + radius >= width) {
			vel = Vec(-vel.x, vel.y)
		}
		if (loc.y - radius <= 0 || loc.y + radius >= height) {
			vel = Vec(vel.x, -vel.y)
		}
	}
}

class Ball(x: Double, y: Double, val mass: Double, val radius: Double) {
	var loc: Vec
	var vel: Vec

	init {
		loc = Vec(x, y)
		vel = Vec(0.0, 0.0)
	}

	fun draw(g2d: Graphics2D) {
		with(g2d) {
			color = Color.WHITE
			fillOval((loc.x - radius).toInt(), (loc.y - radius).toInt(), (radius * 2).toInt(), (radius * 2).toInt())
		}
	}
}
