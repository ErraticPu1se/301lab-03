package com.example.listycity3

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun updateCity_replacesExistingCity() {
        val repository = CityRepository()
        val oldCity = City("Vancouver", "BC")
        val updatedCity = City("Victoria", "British Columbia")

        repository.updateCity(oldCity, updatedCity)

        assertFalse(repository.cities.contains(oldCity))
        assertTrue(repository.cities.contains(updatedCity))
    }

    @Test
    fun updateCity_doesNothingWhenCityIsMissing() {
        val repository = CityRepository()
        val citiesBeforeUpdate = repository.cities.toList()

        repository.updateCity(
            oldCity = City("Montreal", "QC"),
            updatedCity = City("Quebec City", "QC")
        )

        assertEquals(citiesBeforeUpdate, repository.cities)
    }
}
