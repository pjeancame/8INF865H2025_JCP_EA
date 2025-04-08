package com.jcp.viasolis.data

// Liste des sentiers disponibles
val trailsList = listOf(
    Trail("1", "Sentier des Crêtes", "2h30", "8 km", "450m", 2, "Un magnifique sentier avec des vues imprenables sur les montagnes environnantes."),
    Trail("2", "Chemin du Lac", "1h45", "5 km", "200m", 1, "Une balade paisible le long du lac, idéale pour les familles."),
    Trail("3", "Randonnée des Aiguilles", "4h00", "12 km", "750m", 4, "Une randonnée difficile avec un dénivelé important, offrant un panorama exceptionnel."),
    Trail("4", "Vallée des Arbres", "3h15", "10 km", "600m", 3, "Un sentier forestier agréable avec des passages en clairière et une belle diversité de paysages."),
    Trail("5", "Col des Roches", "5h00", "15 km", "850m", 4, "Un itinéraire exigeant pour les randonneurs expérimentés, offrant une vue panoramique à l'arrivée."),
    Trail("6", "Forêt Enchantée", "2h00", "6 km", "300m", 2, "Une promenade accessible avec de magnifiques arbres centenaires et une atmosphère paisible."),
    Trail("7", "Montagne de la Lune", "6h30", "20 km", "1100m", 4, "Un défi pour les passionnés de randonnée, récompensé par un paysage à couper le souffle."),
    Trail("8", "Cascade Mystique", "1h30", "4 km", "150m", 1, "Une courte randonnée menant à une magnifique cascade cachée dans la forêt.")
)

// Fonction pour récupérer un sentier par son ID
fun getTrailById(trailId: String?): Trail? {
    return trailsList.find { it.id == trailId }
}