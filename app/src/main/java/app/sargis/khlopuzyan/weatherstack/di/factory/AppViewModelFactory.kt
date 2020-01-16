package app.sargis.khlopuzyan.weatherstack.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
class AppViewModelFactory(
    private val providers: Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        requireNotNull(getProvider(modelClass).get()) {
            "Provider for $modelClass returned null"
        }

    @Suppress("UNCHECKED_CAST")
    private fun <T : ViewModel> getProvider(modelClass: Class<T>): Provider<T> =
        try {
            requireNotNull(providers[modelClass] as Provider<T>) {
                "No ViewModel provider is bound for class $modelClass"
            }
        } catch (cce: ClassCastException) {
            error("Wrong provider type registered for ViewModel type $modelClass")
        }

}