package top.spencercjh.exercise

import java.util.*

interface Statement<T : Any> {
    val type: String
    val parameterList: List<T>
    val operation: (T) -> String
    fun buildUp(): String = with(StringBuilder()) {
        parameterList.forEach { this.append(operation(it) + " ") }
        return toString()
    }
}

data class CommentStatement<T : Any>(
    override val type: String,
    override val parameterList: List<T>,
    override val operation: (T) -> String
) : Statement<T>

data class DependencyStatement<T : Any>(
    override val type: String,
    override val parameterList: List<T>,
    override val operation: (T) -> String
) : Statement<T>

data class ControlStatement<T : Any>(
    override val type: String,
    override val parameterList: List<T>,
    override val operation: (T) -> String
) : Statement<T>

data class AnalysisStatement<T : Any>(
    override val type: String,
    override val parameterList: List<T>,
    override val operation: (T) -> String
) : Statement<T>

data class ModelStatement<T : Any>(
    override val type: String,
    override val parameterList: List<T>,
    override val operation: (T) -> String
) : Statement<T>

data class SubCircuitStatement<T : Any>(
    override val type: String,
    override val parameterList: List<T>,
    override val operation: (T) -> String
) : Statement<T>

data class CircuitElements<C : Any, M : Any, A : Any, S : Any>(
    val comments: List<CommentStatement<String>>?,
    val dependencies: List<DependencyStatement<String>>?,
    val controlStatements: List<ControlStatement<C>>?,
    val modelStatements: List<ModelStatement<M>>?,
    val analysisStatements: List<AnalysisStatement<A>>?,
    val instanceStatements: List<SubCircuitStatement<S>>?
) {

    data class Builder<C : Any, M : Any, A : Any, S : Any>(
        var comments: List<CommentStatement<String>>? = Collections.emptyList(),
        var dependencies: List<DependencyStatement<String>>? = Collections.emptyList(),
        var controlStatements: List<ControlStatement<C>>? = Collections.emptyList(),
        var modelStatements: List<ModelStatement<M>>? = Collections.emptyList(),
        var analysisStatements: List<AnalysisStatement<A>>? = Collections.emptyList(),
        var instanceStatements: List<SubCircuitStatement<S>>? = Collections.emptyList()
    ) {
        fun comments(comments: List<CommentStatement<String>>) =
            apply { this.comments = comments }

        fun dependencies(dependencies: List<DependencyStatement<String>>) =
            apply { this.dependencies = dependencies }

        fun controlStatements(controlStatements: List<ControlStatement<C>>) =
            apply { this.controlStatements = controlStatements }

        fun modelStatements(modelStatements: List<ModelStatement<M>>) =
            apply { this.modelStatements = modelStatements }

        fun analysisStatements(analysisStatements: List<AnalysisStatement<A>>) =
            apply { this.analysisStatements = analysisStatements }

        fun instanceStatements(instanceStatements: List<SubCircuitStatement<S>>) =
            apply { this.instanceStatements = instanceStatements }

        fun build() = CircuitElements(
            comments,
            dependencies,
            controlStatements,
            modelStatements,
            analysisStatements,
            instanceStatements
        )

    }
}

interface BaseGenerateCircuitDiagram<C : Any, M : Any, A : Any, S : Any> {
    val elements: CircuitElements<C, M, A, S>
    fun generateCircuitDiagram(): String
}

class CircuitDiagram<C : Any, M : Any, A : Any, S : Any>(override val elements: CircuitElements<C, M, A, S>) :
    BaseGenerateCircuitDiagram<C, M, A, S> {
    override fun generateCircuitDiagram(): String {
        val result = StringBuilder()
        // asert them not null
        // 这些代码已经冗余了。靠已经Statement接口，进一步抽象他们
        @Suppress("DuplicatedCode")
        elements.comments!!.forEach { x -> result.append(x.buildUp()) }
        elements.dependencies!!.forEach { x -> result.append(x.buildUp()) }
        elements.instanceStatements!!.forEach { x -> result.append(x.buildUp()) }
        elements.analysisStatements!!.forEach { x -> result.append(x.buildUp()) }
        elements.controlStatements!!.forEach { x -> result.append(x.buildUp()) }
        elements.modelStatements!!.forEach { x -> result.append(x.buildUp()) }
        return result.toString()
    }

}

fun main() {
    val elements = CircuitElements.Builder<String, Number, Number, Number>()
        .comments(
            listOf(
                CommentStatement(
                    type = "author",
                    parameterList = listOf("Spencer", "Jack"),
                    operation = { x: String -> x.toUpperCase() }),
                CommentStatement("other", Collections.emptyList()) { x -> x }
            )
        )
        .dependencies(
            listOf(
                DependencyStatement(
                    type = "include",
                    parameterList = listOf("stdio", "something else"),
                    operation = { x: String -> "<$x>" }),
                DependencyStatement("other", Collections.emptyList()) { x -> x }
            )
        )
        .instanceStatements(
            listOf(
                SubCircuitStatement(
                    type = "circuit1",
                    parameterList = listOf<Number>(-1, 5.2, 23, 78.8),
                    operation = { x -> "$x" }
                )))
        .analysisStatements(
            listOf(
                AnalysisStatement(
                    type = "analysis1",
                    parameterList = listOf<Number>(123, -5, 6.123, 123.512, -5124),
                    operation = { x -> "= $x" }
                )
            ))
        .controlStatements(
            listOf(
                ControlStatement(
                    type = "control1",
                    parameterList = listOf("run", "shutdown", "suspend"),
                    operation = { x -> "$x;" }
                )
            )
        )
        .modelStatements(
            listOf(
                ModelStatement(
                    type = "model1",
                    parameterList = listOf<Number>(123, -123, 12.45),
                    operation = { x -> "* $x" }
                )
            )
        )
        .analysisStatements(
            listOf(
                AnalysisStatement(
                    type = "analysis1",
                    parameterList = listOf<Number>(123, -1231, 123.5, 35.414),
                    operation = { x -> "$ $x" }
                )
            )
        )
        .build()
    val service = CircuitDiagram(elements)
    service.generateCircuitDiagram()
}