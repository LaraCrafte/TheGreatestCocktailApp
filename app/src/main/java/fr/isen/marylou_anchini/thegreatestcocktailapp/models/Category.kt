package fr.isen.marylou_anchini.thegreatestcocktailapp.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import fr.isen.marylou_anchini.thegreatestcocktailapp.R

enum class Category {
    BEER,
    COCKTAIL,
    COCOA,
    COFFEE,
    LIQUOR,
    DRINK,
    PUNCH,
    SHAKE,
    SHOT,
    SOFT,
    ALCOHOLIC,
    MOCKTAILS,
    OTHER;

    companion object {
        fun allObjects(): List<Category> {
            return listOf(
                BEER,
                COCKTAIL,
                COCOA,
                COFFEE,
                LIQUOR,
                DRINK,
                PUNCH,
                SHAKE,
                SHOT,
                SOFT,
                MOCKTAILS,
                OTHER
            )
        }

        fun toString(category: Category): String {
            return when(category) {
                ALCOHOLIC -> "Alcoholic"
                MOCKTAILS -> "Non alcoholic"
                OTHER -> "Other / Unknown"
                BEER -> "Beer"
                COCKTAIL -> "Cocktail"
                COCOA -> "Cocoa"
                COFFEE -> "Coffee"
                LIQUOR -> "Homemade Liquor"
                DRINK -> "Ordinary Drink"
                PUNCH -> "Punch / Party Drink"
                SHAKE -> "Shake"
                SHOT -> "Shot"
                SOFT -> "Soft Drink"
            }
        }


        @Composable
        fun colors(category: Category): List<Color> {
            return when(category) {
                ALCOHOLIC -> listOf(
                    colorResource(R.color.orange),
                    colorResource(R.color.orange)
                )

                MOCKTAILS -> listOf(
                    colorResource(R.color.orange),
                    colorResource(R.color.orange)
                )

                OTHER -> listOf(
                    colorResource(R.color.light_green),
                    colorResource(R.color.light_green)
                )
                BEER -> listOf(
                    colorResource(R.color.light_green),
                    colorResource(R.color.light_green)
                )
                COCKTAIL -> listOf(
                    colorResource(R.color.light_green),
                    colorResource(R.color.light_green)
                )
                COCOA -> listOf(
                    colorResource(R.color.light_green),
                    colorResource(R.color.light_green)
                )
                COFFEE -> listOf(
                    colorResource(R.color.light_green),
                    colorResource(R.color.light_green)
                )
                LIQUOR -> listOf(
                    colorResource(R.color.light_green),
                    colorResource(R.color.light_green)
                )
                DRINK -> listOf(
                    colorResource(R.color.light_green),
                    colorResource(R.color.light_green)
                )
                PUNCH -> listOf(
                    colorResource(R.color.light_green),
                    colorResource(R.color.light_green)
                )
                SHAKE -> listOf(
                    colorResource(R.color.light_green),
                    colorResource(R.color.light_green)
                )
                SHOT -> listOf(
                    colorResource(R.color.light_green),
                    colorResource(R.color.light_green)
                )
                SOFT -> listOf(
                    colorResource(R.color.light_green),
                    colorResource(R.color.light_green)
                )
            }
        }
    }
}