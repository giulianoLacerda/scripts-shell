import java.io.File

fun ClosedRange<Int>.random() = (Math.random() * ((endInclusive + 1) - start) + start).toInt()
fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)

fun oversample(name: String, dir: File, images: List<File>, amount: Int) {
    val outDir = File(dir, if(name == "Normal") "Normal" else "Defeito").apply{mkdirs()}
    val initialCopy = Math.min(amount, images.size)
    var index = 0

    for(i in 0 until initialCopy) {
        images[i].copyTo(File(outDir, "$name-$index.png"))
        index++
        
        if(index%1000 == 0) {
          print("\r${(index.toDouble()/amount*100.0).format(1)}% [$index/$amount]")
        }
    }

    val imagesLeft = amount - initialCopy

    for(i in 0 until imagesLeft) {
        val pos = (0 until images.size).random()
        images[pos].copyTo(File(outDir, "$name-$index.png"))
        index++
        if(index%1000 == 0) {
          print("\r${(index.toDouble()/amount*100.0).format(1)}% [$index/$amount]")
        }
    }
}

fun main(args: Array<String>) {
    val root = File("/home/camaro/Documents/MXNet/dockerfilemxnet/dockerhome/bases/20181207")
    val output = File("/home/camaro/Documents/MXNet/dockerfilemxnet/dockerhome/bases/20181207-oversampled")
    //val totalTrain = 10000
    val percentVal = 15
    val percentTest = 5
    /*val totals = mutableMapOf(
            "p12" to 6000,
            "p13" to 6000,
            "p14" to 6000,
            "p15" to 6000,
            "p16" to 6000,
            "p17" to 6000,
            "p18" to 6000,
            "p19" to 6000
            //"Normal" to 30000
    )*/
    /*val totals = mutableMapOf(
            "Quebrado" to 15000,
            "Verde" to 15000,
            "Ardido" to 15000,
            "Chocho" to 15000,
            "Brocado" to 15000,
            "Preto" to 15000,
            "Casca" to 15000,
            "Pedra" to 15000,
            "Coco" to 15000,
            "Pau" to 15000,
            "Concha" to 15000,
            "Marinheiro" to 15000
            //"Normal" to 30000
    )*/
    /*val totals = mutableMapOf(
            "Quebrado" to 40000,
            "Verde" to 50000,
            "Ardido" to 50000,
            "Chocho" to 50000,
            "Brocado" to 50000,
            "Preto" to 40000,
            "Casca" to 40000,
            "Pedra" to 40000,
            "Coco" to 40000,
            "Pau" to 40000,
            "Concha" to 50000,
            "Marinheiro" to 40000
    )*/
    val totals = mutableMapOf(
            "Quebrado" to 40000,
            "Verde" to 60000,
            "Ardido" to 60000,
            "Chocho" to 60000,
            "Brocado" to 40000,
            "Preto" to 40000,
            "Casca" to 40000,
            "Pedra" to 40000,
            "Coco" to 40000,
            "Pau" to 40000,
            "Concha" to 40000,
            "Marinheiro" to 40000
    )
    /*val totals = mapOf(
            "p12" to 15000,
            "p13" to 15000,
            "p14" to 15000,
            "p15" to 15000,
            "p16" to 15000,
            "p17" to 15000,
            "p18" to 15000,
            "p19" to 15000
    )*/
    /*val totals = mapOf(
            "p12" to 6000,
            "p13" to 6000,
            "p14" to 6000,
            "p15" to 6000,
            "p16" to 6000,
            "p17" to 6000,
            "p18" to 6000,
            "p19" to 6000
    )*/

    totals["Normal"] = totals.values.sum()

    //val totalVal = (percentVal/100.0*totalTrain).toInt()
    //val totalTest = (percentTest/100.0*totalTrain).toInt()

    for(dir in root.listFiles(File::isDirectory)) {
        val name = dir.name
        val total = totals[name] ?: continue
        val images = dir.walkBottomUp().filter{ it.isFile && it.extension == "png"}.toList().shuffled()

        val numVal = (percentVal/100.0*images.size).toInt()
        val numTest = (percentTest/100.0*images.size).toInt()
        val numTrain = images.size-numVal-numTest

        val trainImages = images.subList(0, numTrain)
        val valImages = images.subList(numTrain, numTrain+numVal)
        val testImages = images.subList(numTrain+numVal, numTrain+numVal+numTest)

        val totalVal = (total*percentVal/100.0).toInt()
        val totalTest = (total*percentTest/100.0).toInt()
        val totalTrain = total-totalVal-totalTest
        println("\nClasse: $name\nGerando base de treino...")
        oversample(name, File(output, "train"), trainImages, totalTrain)
        println("\nGerando base de validação...")
        oversample(name, File(output, "val"), valImages, totalVal)
        println("\nGerando base de teste...")
        oversample(name, File(output, "test"), testImages, totalTest)
    }
}
