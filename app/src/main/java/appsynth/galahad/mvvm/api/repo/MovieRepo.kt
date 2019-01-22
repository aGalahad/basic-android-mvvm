package appsynth.galahad.mvvm.api.repo

import appsynth.galahad.mvvm.model.MovieDetail
import io.reactivex.Observable

interface MovieRepo {
    fun getMovieDetail(id: String) : Observable<MovieDetail>
    fun getRelateMovie(id: String) : Observable<List<MovieDetail>>
}

class MovieRepoImpl : MovieRepo {

    private val movieList = mutableMapOf<String, MovieDetail>()

    init {
        movieList["harry_1"] = MovieDetail().apply {
            id = "harry_1"
            name = "Harry Potter and the Philosopher's Stone"
            desc = "แฮร์รี่อาศัยอยู่กับบ้านลุงกับป้าและลูกพี่ลูกน้องของเขามาเป็นเวลา 10 ปี วันหนึ่งได้เกิดเหตุการณ์ประหลาดเกิดขึ้นกับเขา เขาเผลอปล่อยงูใส่ลูกพี่ลูกน้องขณะไปเที่ยวสวนสัตว์ หลังจากนั้นเขาได้รับจดหมายฉบับหนึ่ง แต่ลูกกับป้าเห็นเข้าเลยเอาไปทำลาย แต่จดหมายก็ถูกส่งมาเรี่อยๆ จนพวกเขาต้องย้ายไปอยู่ในกระท่อมกลางเกาะ ในคืนวันเกิดแฮร์รี่"
            posterUrl = "https://charliederry.files.wordpress.com/2001/04/philostone.jpg?w=1013"
            playUrl = "VyHV0BRtdxo"
        }

        movieList["harry_2"] = MovieDetail().apply {
            id = "harry_2"
            name = "Harry Potter and the Chamber of Secrets"
            desc = "ในปีที่ 2 ของแฮร์รี่ปีนี้ เขาไม่ได้รับจดหมายจากเพื่อนของเขาคนไหนเลย และพบกับเอลฟ์ประจำบ้านที่ชื่อด๊อบบี้ที่มาเตือนภัยแฮร์รี่ ว่าปีนี้มีการวางแผน แผนที่ทำให้เกิดสิ่งเลวร้ายมาก และแฮร์รี่จะต้องไม่กลับไปโรงเรียนของเขา ในปีนี้ แต่แฮร์รี่ไม่ยอม จนเอลฟ์เสกขนมพุ๊ดดิ้งให้ลอยแล้วตกใส่หัวของแขกของลุงกับป้าเขา จนลุงของเขาขังแฮร์รี่ไว้ไม่ให้ไปไหน"
            posterUrl = "https://i.pinimg.com/originals/fb/7e/e7/fb7ee741aa5d3e0b9f3054bef59f75d2.jpg"
            playUrl = "1bq0qff4iF8"
        }

        movieList["harry_3"] = MovieDetail().apply {
            id = "harry_3"
            name = "Harry Potter and the Prisoner of Azkaban"
            desc = "แฮร์รี่ กำลังปิดเทอมอยู่บ้าน ในขณะนั้นป้ามาร์จ พี่ของลุงเวอร์นอน มาเยี่ยมบ้าน เมื่อมาถึงบ้านป้ามาร์จเริ่มด่าแฮร์รี่ แฮร์รี่พยายามทนเพราะท่าเขาทำตัวดี ลุงจะเซ็นใบตอบรับการไปฮอกส์มีดของนักเรียนปีสามให้ แต่เมื่อป้ามาร์จเริ่มด่าแฮร์รี่และพูดถึงพ่อแม่เขาต่างๆนานา แฮร์รี่จึงทนไม่ได้เสกคาถาเป่าลมใส่คุณป้าจนลอยออกไป"
            posterUrl = "https://www.movie2free.com/wp-content/uploads/thumb/harrypotter3.jpg"
            playUrl = "lAxgztbYDbs"
        }

        movieList["harry_4"] = MovieDetail().apply {
            id = "harry_4"
            name = "Harry Potter and the Goblet of Fire"
            desc = "ในขณะการแข่งขันควิดดิช ได้เกิดบางอย่างเหนือท้องฟ้าที่ตั้งแคมพ์กับผู้ชมการแข่งขัน คือตรามาร สัญลักษณ์ของลอร์ดโวลเดอมอร์ ซึ่งถูกเสกขึ้นมาโดยผู้เสพความตาย สาวกของเขาซึ่งไม่เคยกล้าปรากฏตัวในที่สาธารณะ ตั้งแต่ครั้งที่มีผู้เห็น โวลเดอมอร์ เป็นครั้งสุดท้ายเมื่อสิบสามปีก่อน ในคืนที่เขาสังหารพ่อแม่ของแฮร์รี่"
            posterUrl = "https://i.pinimg.com/originals/7f/f1/a7/7ff1a787d1242ad22ba3eb46fe5c11b3.jpg"
            playUrl = "3EGojp4Hh6I"
        }

        movieList["harry_5"] = MovieDetail().apply {
            id = "harry_5"
            name = "Harry Potter and the Order of the Phoenix"
            desc = "แฮร์รี่ต้องแก้ข้อกล่าวหาจนถึงที่สุด ต่อหน้าศาลโดยมีคอร์นีเลียส ฟัดจ์ รัฐมนตรีกระทรวงเวทมนตร์ ที่เลือกปฏิบัติในการกำกับ แต่แฮร์รี่ถูกตัดสินให้พ้นผิด ต่อมาแฮร์รี่กลับมายังฮอกวอตส์ ซึ่งทำให้ความน่าเชื่อถือของแฮร์รี่ถูกลดทอนลง เพราะเรื่องโกหกเกี่ยวกับเรื่องการเผชิญหน้ากับครั้งล่าสุดของหนุ่มวัยรุ่นและโวลเดอมอร์"
            posterUrl = "https://i.pinimg.com/originals/9a/8a/b9/9a8ab983d8d6d594bd2afaf1792b99c6.jpg"
            playUrl = "y6ZW7KXaXYk"
        }

        movieList["harry_6"] = MovieDetail().apply {
            id = "harry_6"
            name = "Harry Potter and the Half-Blood Prince"
            desc = "หลังจากโลกเวทมนตร์รับรู้ว่าลอร์ดโวลเดอมอร์ได้กลับคืนสู่โลกเวทมนตร์อีกครั้ง ผู้คนต่างหวาดกลัวและมีข่าวการหายตัวไปของผู้คนออกมาเรื่อยๆ ภาพยนตร์เปิดฉากด้วยการถล่มสะพานมิลเลนเนียมของเหล่าผู้เสพความตายและฉากลักพาตัวโอลลิแวนเดอร์จากตรอกไดแอกอนซึ่งในหนังสือมีการบรรยายไว้สั้นๆ"
            posterUrl = "https://i.pinimg.com/originals/72/cd/b1/72cdb127fc2333454ee76438b63e7a6a.jpg"
            playUrl = "sg81Lts5kYY"
        }

        movieList["harry_7"] = MovieDetail().apply {
            id = "harry_7"
            name = "Harry Potter and the Deathly Hallows"
            desc = "เรื่องเริ่มต้นที่บ้านของลูเซียส มัลฟอย โดยลอร์ดโวลเดอมอร์ และสมุนจำนวนหนึ่ง วางแผนการเกี่ยวกับการย้ายออกจากบ้านเดอร์สลี่ย์ของแฮร์รี่ พอตเตอร์ เจ้าหน้าที่ของกระทรวงชื่อแยกซ์ลีย์ ระบุว่าแฮร์รี่จะย้ายออกในวันคล้ายวันเกิด ในขณะที่สเนประบุว่าแฮร์รี่จะย้ายออกก่อนหน้านั้นหนึ่งสัปดาห์"
            posterUrl = "https://s.isanook.com/mv/0/ui/2/11980/18961_002.jpg"
            playUrl = "MxqsmsA8y5k"
        }
    }

    override fun getMovieDetail(id: String): Observable<MovieDetail> {
        return Observable.just(movieList[id])
    }

    override fun getRelateMovie(id: String): Observable<List<MovieDetail>> {

        val result = mutableListOf<MovieDetail>()
        movieList.forEach { movie ->
            if (movie.key != id) {
                result.add(movie.value)
            }
        }

        return Observable.just(result)
    }
}