/*
Copyright 2021 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package br.com.mathsemilio.simpleapodbrowser.ui.container

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.ui.common.BaseActivity
import br.com.mathsemilio.simpleapodbrowser.ui.common.delegate.ContainerLayoutDelegate
import br.com.mathsemilio.simpleapodbrowser.ui.common.permission.PermissionHandler
import br.com.mathsemilio.simpleapodbrowser.ui.common.util.launchWebPage
import br.com.mathsemilio.simpleapodbrowser.ui.container.view.MainActivityView
import br.com.mathsemilio.simpleapodbrowser.ui.container.view.MainActivityViewImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), ContainerLayoutDelegate {

    private lateinit var view: MainActivityView

    private lateinit var navController: NavController

    private lateinit var permissionHandler: PermissionHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        view = MainActivityViewImpl(layoutInflater, parent = null)
        permissionHandler = compositionRoot.permissionHandler

        setContentView(view.rootView)

        view.adjustWindowInsets()

        setupNavController()

        setupUIComponentsWithNavController()

        setOnDestinationChangedListener()
    }

    override val navHostFragment: NavHostFragment
        get() = getNavHostFragmentFromContainer()

    override val fragmentContainer
        get() = view.fragmentContainer

    override val bottomNavigationView
        get() = view.bottomNavigationView

    private fun setupNavController() {
        val navHostFragment = getNavHostFragmentFromContainer()
        navController = navHostFragment.findNavController()
    }

    private fun getNavHostFragmentFromContainer(): NavHostFragment {
        return supportFragmentManager.findFragmentById(
            R.id.fragment_container_view_app
        ) as NavHostFragment
    }

    private fun setupUIComponentsWithNavController() {
        setSupportActionBar(view.toolbar)

        setupActionBarWithNavController(
            navController,
            AppBarConfiguration(
                topLevelDestinationIds = setOf(R.id.LatestApodsScreen, R.id.FavoriteApodsScreen)
            )
        )

        view.bottomNavigationView.setupWithNavController(navController)
    }

    private fun setOnDestinationChangedListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.ApodDetailScreen -> view.hideBottomNavigationView()
                R.id.SettingsScreen -> view.hideBottomNavigationView()
                else -> showTopLevelViews()
            }
        }
    }

    private fun showTopLevelViews() {
        view.showToolbar()
        view.showBottomNavigationView()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHandler.onRequestPermissionResult(requestCode, permissions, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_app, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_global_action_visit_apod_website -> {
                launchWebPage(getString(R.string.apod_website_url))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp() = navController.navigateUp() || super.onSupportNavigateUp()
}
