package task1

// import org.apache.spark.sql.SparkSession
import java.text.SimpleDateFormat
import java.util.TimeZone

object Helper extends Serializable {
  def isValid(line: String): Boolean = {
    val fields = line.split(" ")
    fields.length == 7 && fields(0).matches("\\d\\.\\d\\d\\d") && fields(0).toDouble > 0 && fields(6).matches("[0-9]+") && fields(6).toInt > 0 && fields(2) != "-"
  }

  def parseTime(line: String, timeFormat: SimpleDateFormat): Long = {
    val fields = line.split(" ")
    timeFormat.parse(s"${fields(3)} ${fields(4)}").getTime()
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    // val spark = SparkSession.builder()
    //   .appName("Task 1")
    //   .getOrCreate();
    // spark.sparkContext.setLogLevel("ERROR")
    // val sc = spark.sparkContext

    val timeFormat = new SimpleDateFormat("[dd/MMM/yyyy:HH:mm:ss z]")
    timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"))

    // val lineRDD = sc.textFile("hdfs:///share/FPT-2018-12-02.log")
    val lineRDD = sc.textFile("file:///share/FPT-2018-12-02.log")
    val correctLineRDD = lineRDD.filter(Helper.isValid(_))
    val wrongLineRDD = lineRDD.filter(!Helper.isValid(_))

    println(s"Number of lines: ${lineRDD.count()}")
    println(s"Number of wrong lines: ${wrongLineRDD.count()}")

    println("Top 10 correct lines sorted by response time:")
    correctLineRDD.sortBy(line => Helper.parseTime(line, timeFormat)).take(10).foreach(println)

    println("Top 10 wrong lines sorted by response time:")
    wrongLineRDD.sortBy(line => Helper.parseTime(line, timeFormat)).take(10).foreach(println)
  }
}
