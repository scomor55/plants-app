package com.example.rma24projekat_19153

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var db: BiljkaDatabase
    private lateinit var biljkaDAO: BiljkaDAO

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, BiljkaDatabase::class.java).build()
        biljkaDAO = db.biljkaDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertAndGetBiljka() = runBlocking {
        val biljka = Biljka(
            id = 1,
            naziv = "Test Biljka",
            porodica = "Test Porodica",
            medicinskoUpozorenje = "Nema upozorenja",
            medicinskeKoristi = listOf(),
            profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
            jela = listOf(),
            klimatskiTipovi = listOf(),
            zemljisniTipovi = listOf()
        )
        biljkaDAO.insertBiljka(biljka)
        val allBiljkas = biljkaDAO.getAllBiljkas()
        assert(allBiljkas.isNotEmpty())
        assert(allBiljkas[0] == biljka)
    }

    @Test
    fun testClearData() = runBlocking {
        val biljka = Biljka(
            id = 1,
            naziv = "Test Biljka",
            porodica = "Test Porodica",
            medicinskoUpozorenje = "Nema upozorenja",
            medicinskeKoristi = listOf(),
            profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
            jela = listOf(),
            klimatskiTipovi = listOf(),
            zemljisniTipovi = listOf()
        )
        biljkaDAO.insertBiljka(biljka)
        biljkaDAO.clearData()
        val allBiljkas = biljkaDAO.getAllBiljkas()
        assert(allBiljkas.isEmpty())
    }
}