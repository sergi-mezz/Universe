package com.smdev.universe.planetbrowser.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.smdev.universe.R
import com.smdev.universe.data.Planet
import com.smdev.universe.databinding.HolderPlanetBinding

class PlanetsListAdapter(
    private val planetsList: List<Planet>,
    val onCardClicked: (String) -> Unit) :
    RecyclerView.Adapter<PlanetsListAdapter.PlanetHolder>() {

    private val randomIconList = arrayOf(
        R.drawable.ic_airplane_ticket,
        R.drawable.ic_rocket,
        R.drawable.ic_star_24,
        R.drawable.ic_sun,
        R.drawable.ic_travel_explore,
        R.drawable.ic_airplane_ticket,
        R.drawable.ic_shield_moon,
        R.drawable.ic_star_24
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetHolder {
        return PlanetHolder(
            HolderPlanetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = planetsList.size

    override fun onBindViewHolder(holder: PlanetHolder, position: Int) {
        val planet = planetsList[position]
        with(holder.view) {
            nameTextView.text = planet.name
            climateTextView.text = planet.climate
            populationTextView.text = planet.population
            root.setOnClickListener { onCardClicked(planet.url) }
            planetImageView.setImageDrawable(
                AppCompatResources.getDrawable(root.context,
                    randomIconList.random()
                )
            )
        }
    }

    data class PlanetHolder(val view: HolderPlanetBinding) : ViewHolder(view.root)
}