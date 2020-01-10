package com.sngular.wheatherapp.data.mapper.current

import com.sngular.core.arch.transformer.Transformer
import com.sngular.wheatherapp.data.models.current.Main
import com.sngular.wheatherapp.domain.models.current.Temperature

class MainTemperatureToTemperature : Transformer<Main, Temperature>() {

    override fun transform(value: Main): Temperature {
        return Temperature(
            current = (value.feelsLike ?: 0).toString(), max = (value.tempMax ?: 0).toString(),
            min = (value.tempMin ?: 0).toString()
        )
    }
}