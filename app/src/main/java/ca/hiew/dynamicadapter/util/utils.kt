package ca.hiew.dynamicadapter.util

/** kotlin when expressions are only exhaustive if used as an expression and not as a statement.
 * you can get around this by adding !! to the end of the when statement but I feel that the intention
 * is not always clear. This helper is used used with when to help with intentions
 */
val <T> T.exhaustive: T
    get() = this

inline fun <reified T> T.areTheSame(o: Any?, onMatchingTypes: (T) -> Boolean): Boolean {
    return if (o is T) {
        onMatchingTypes(o)
    } else false
}
