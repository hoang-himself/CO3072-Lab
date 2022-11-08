package task2

// import org.apache.spark.sql.SparkSession

object Helper extends Serializable {
  def isValid(line: String): Boolean = {
    val fields = line.split(" ")
    fields.length == 7 && fields(0).matches("\\d\\.\\d\\d\\d") && fields(0).toDouble > 0 && fields(6).matches("[0-9]+") && fields(6).toInt > 0 && fields(2) != "-"
  }

  def mapToService(line: String): String = {
    line.split(" ")(5) match {
      case x if x.matches("^.*\\.(mpd|m3u8)$") => "HLS"
      case x if x.matches("^.*\\.(dash|ts)$") => "MPEG-DASH"
      case _ => "Web service"
    }
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    // val spark = SparkSession.builder()
    //   .appName("Task 1")
    //   .getOrCreate();
    // spark.sparkContext.setLogLevel("ERROR")
    // val sc = spark.sparkContext

    // val lineRDD = sc.textFile("hdfs:///share/FPT-2018-12-02.log")
    val lineRDD = sc.textFile("file:///share/FPT-2018-12-02.log")
    val ipDictDF = spark.read.option("header", true).csv("file:///share/IPDict.csv")
    val correctLineRDD = lineRDD.filter(Helper.isValid(_))

    val hlsRDD = correctLineRDD.filter(line => Helper.mapToService(line) == "HLS")
    val dashRDD = correctLineRDD.filter(line => Helper.mapToService(line) == "MPEG-DASH")
    val webServiceRDD = correctLineRDD.filter(line => Helper.mapToService(line) == "Web service")

    println(s"Number of HLS records: ${hlsRDD.count()}")
    println(s"Number of MPEG-DASH records: ${dashRDD.count()}")
    println(s"Number of web service records: ${webServiceRDD.count()}")

    println("List of unique IP addresses:")
    correctLineRDD.map(line => line.split(" ")(1)).distinct().collect().foreach(println)
  }
}
