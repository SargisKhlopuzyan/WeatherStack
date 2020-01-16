package app.sargis.khlopuzyan.weatherstack.di.annotation

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ViewModelKey(
    val value: KClass<out ViewModel>
)