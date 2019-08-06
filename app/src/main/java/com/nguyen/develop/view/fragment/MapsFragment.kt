package com.nguyen.develop.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.SearchView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.nguyen.develop.R
import com.nguyen.develop.extensions.hiddenSoftKeyBoard
import com.nguyen.develop.extensions.showToast
import com.nguyen.develop.model.PlaceInfo
import com.nguyen.develop.view.adapter.PlaceAutocompleteAdapter
import kotlinx.android.synthetic.main.fragment_maps.*
import java.io.IOException
import java.util.*

@Suppress("CAST_NEVER_SUCCEEDS")
class MapsFragment : BaseFragment() {

    companion object {

        private const val DEFAULT_ZOOM = 10f
        private val BOUNDS_GREATER_SYDNEY = LatLngBounds(
                LatLng(-34.041458, 150.790100), LatLng(-33.682247, 151.383362))
        private var locationPermissionGranted = false
        const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        const val REQUEST_CONNECTION_ERROR_KEY = 404
        val instance: MapsFragment by lazy {
            MapsFragment()
        }
    }

    private var marker: Marker? = null
    private var googleMap: GoogleMap? = null
    private val lastSearchPlace = PlaceInfo()
    private lateinit var geoDataClient: GeoDataClient
    //    private lateinit var googleApiClient: GoogleApiClient
    private lateinit var searchAutocomplete: SearchView.SearchAutoComplete
    private lateinit var googleSearchAdapter: PlaceAutocompleteAdapter
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!checkGoogleServicesSDK())
            showToast("Can't use google SDK")
        else {
            getLocationPermission()
        }
        initView()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                    initGoogleMap()
                }
            }
        }
    }

    /**
     * Set listener for current view
     */
    private fun initView() {
        //Get current location when click gps button
        btnGps.setOnClickListener {
            getCurrentLocation()
        }
        // Search
        geoDataClient = Places.getGeoDataClient(requireActivity())
        googleSearchAdapter = PlaceAutocompleteAdapter(requireContext(),
                geoDataClient, BOUNDS_GREATER_SYDNEY, null)
        searchAutocomplete =
                mapSearchView.findViewById(androidx.appcompat.R.id.search_src_text)
        searchAutocomplete.setAdapter(googleSearchAdapter)
        searchAutocomplete.setOnItemClickListener { _, _, i, _ ->
            hiddenSoftKeyBoard()
            val item = googleSearchAdapter.getItem(i)
            val id = item.placeId
            val play = geoDataClient.getPlaceById(id)
            play.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val place = task.result!![0]
                    lastSearchPlace.id = place.id
                    lastSearchPlace.name = place.name.toString()
                    lastSearchPlace.phoneNumber = place.phoneNumber.toString()
                    lastSearchPlace.latLng = place.latLng
                    lastSearchPlace.locate = place.locale
                    lastSearchPlace.rating = place.rating
                    lastSearchPlace.websiteUri = place.websiteUri ?: Uri.EMPTY
                    lastSearchPlace.address = place.address.toString()
                    lastSearchPlace.attributions = place.attributions.toString()
                    place.locale
                    moveCamera(lastSearchPlace.latLng)
                    createMaker(lastSearchPlace.latLng, lastSearchPlace)
                    mapSearchView.setQuery(lastSearchPlace.name, false)
                    searchAutocomplete.dismissDropDown()
                } else {
                    showToast("Place not found")
                }
            }
        }
    }

    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
            initGoogleMap()
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    private fun initGoogleMap() {
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(mapCallback)
    }

    @SuppressLint("MissingPermission")
    private val mapCallback = OnMapReadyCallback { map: GoogleMap ->
        googleMap = map
        /// Turn on the My Location layer and the related control on the map.
        if (locationPermissionGranted) {
            googleMap?.isMyLocationEnabled = true
            googleMap?.uiSettings?.isMyLocationButtonEnabled = false
            moveGoogleMapCompass()
            googleMap?.setOnMapLongClickListener { latLng ->
                createMaker(latLng)
            }
            //Custom style
//            googleMap?.setMapStyle(
//                    MapStyleOptions.loadRawResourceStyle(
//                            requireContext(), R.raw.google_style))
            googleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            mapSearchView.setOnQueryTextListener(onSearchQueryTextListener)
        } else {
            googleMap?.isMyLocationEnabled = false
            googleMap?.uiSettings?.isMyLocationButtonEnabled = false
            getLocationPermission()
        }
        // Get the current location of the device and set the position of the map.
        getCurrentLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (locationPermissionGranted) {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult?.addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result != null) {
                        moveCamera(LatLng(it.result!!.latitude, it.result!!.longitude))
                        showToast("Get location success")
                    } else {
                        showToast("Please enable GPS")
                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }

                } else {
                    showToast("Get location failue")
                }
            }
        }
    }

    /**
     * @param latLng the location camera move to
     * @param zoom zoom the map
     */
    private fun moveCamera(latLng: LatLng, zoom: Float = DEFAULT_ZOOM) {
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    private fun checkGoogleServicesSDK(): Boolean {
        val connection = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity)
        when {
            connection == ConnectionResult.SUCCESS -> //OK
                return true
            GoogleApiAvailability.getInstance().isUserResolvableError(connection) -> //Show dialog to resolve
                GoogleApiAvailability.getInstance().getErrorDialog(activity!!, connection,
                        REQUEST_CONNECTION_ERROR_KEY).show()
            else -> showToast("Can't use google SDK")
        }
        return false
    }

    private fun createMaker(latLng: LatLng, placeInfo: PlaceInfo? = null) {
        //Clear current marker
        googleMap?.clear()
        val snippet = StringBuilder()
        placeInfo?.let {
            snippet.append("Address: ${it.address}\nPhoneNumber: ${it.phoneNumber}\nWebsite: " +
                    "${it.websiteUri}\nPrice rating: ${it.rating}")
        }
        val markerOptions = MarkerOptions().position(latLng)
                .title(placeInfo?.name)
                .snippet(snippet.toString())
        marker = googleMap?.addMarker(markerOptions)
    }

    private fun moveGoogleMapCompass() {
        val googleMapCompass =
                (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).view?.findViewWithTag<View>("GoogleMapCompass")
        googleMapCompass?.let {
            val layoutParams = it.layoutParams as RelativeLayout.LayoutParams
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END)
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START, 0)
            layoutParams.topMargin = resources.getDimension(R.dimen._50sdp).toInt()
            layoutParams.rightMargin = resources.getDimension(R.dimen._10sdp).toInt()
        }
    }

    private val onSearchQueryTextListener =
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    p0?.let {
                        val geoCoder = Geocoder(context)
                        try {
                            val address = geoCoder.getFromLocationName(it, 1)
                            //Find a result
                            if (address.size > 0) {
                                val latLng = LatLng(address[0].latitude, address[0].longitude)
                                lastSearchPlace.id = ""
                                lastSearchPlace.name = address[0].featureName ?: ""
                                lastSearchPlace.phoneNumber = address[0].phone ?: ""
                                lastSearchPlace.latLng = latLng
                                lastSearchPlace.locate = address[0].locale ?: Locale.US
                                lastSearchPlace.rating = 0.0f
                                lastSearchPlace.websiteUri = Uri.EMPTY
                                lastSearchPlace.address = address[0].getAddressLine(0) ?: ""
                                lastSearchPlace.attributions = ""
                                moveCamera(latLng)
                                createMaker(latLng, lastSearchPlace)
                            } else {
                                showToast(getString(R.string.query_no_result))
                            }
                        } catch (ex: IOException) {
                            showToast("GeoCoder IOException: $ex")
                        }
                    }
                    searchAutocomplete.dismissDropDown()
                    hiddenSoftKeyBoard()
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return true
                }

            }
}