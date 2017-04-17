package com.soywiz.korge.render

import com.soywiz.korag.log.LogAG
import org.junit.Assert
import org.junit.Test

class BatchBuilderTest {
    val ag = LogAG(16, 16)
    val bb = BatchBuilder2D(ag)

    @Test
    fun simpleBatch() {
        val tex = Texture(ag.createTexture(), 100, 100)
        bb.addQuad(tex, 0f, 0f)
        bb.flush()

        Assert.assertEquals(
                """
                createTexture():0
                createBuffer(VERTEX):0
                Buffer[0].afterSetMem(mem[80])
                createBuffer(INDEX):1
                Buffer[1].afterSetMem(mem[12])
                draw(vertices=Buffer[0], indices=Buffer[1], program=Program(name=BatchBuilder2D.Tinted, attributes=[a_Tex, a_Col, a_Pos], uniforms=[u_ProjMat, u_Tex]), type=TRIANGLES, vertexLayout=VertexLayout[a_Pos, a_Tex, a_Col], vertexCount=6, offset=0, blending=BlendFactors(srcRGB=SOURCE_ALPHA, dstRGB=ONE_MINUS_SOURCE_ALPHA, srcA=ONE, dstA=ONE_MINUS_SOURCE_ALPHA), uniforms={Uniform(u_ProjMat)=Matrix4([0.125, 0.0, 0.0, 0.0, 0.0, -0.125, 0.0, 0.0, 0.0, 0.0, -1.0, 0.0, -1.0, 1.0, -0.0, 1.0]), Uniform(u_Tex)=TextureUnit(texture=Texture[0], linear=true)})
                ::draw.indices=[0, 1, 2, 3, 0, 2]
                ::draw.vertex[0]: a_Pos[vec2(0.0,0.0)], a_Tex[vec2(0.0,0.0)], a_Col[byte4(-1)]
                ::draw.vertex[1]: a_Pos[vec2(100.0,0.0)], a_Tex[vec2(1.0,0.0)], a_Col[byte4(-1)]
                ::draw.vertex[2]: a_Pos[vec2(100.0,100.0)], a_Tex[vec2(1.0,1.0)], a_Col[byte4(-1)]
                ::draw.vertex[3]: a_Pos[vec2(0.0,100.0)], a_Tex[vec2(0.0,1.0)], a_Col[byte4(-1)]
                Buffer[1].close()
                Buffer[0].close()
			""".trimIndent(),
                ag.getLogAsString()
        )
    }

    @Test
    fun batch2() {
        val tex = Texture(ag.createTexture(), 100, 100)
        bb.addQuad(tex, 0f, 0f)
        bb.addQuad(tex, 100f, 0f)
        bb.flush()

        Assert.assertEquals(
                """
                createTexture():0
                createBuffer(VERTEX):0
                Buffer[0].afterSetMem(mem[160])
                createBuffer(INDEX):1
                Buffer[1].afterSetMem(mem[24])
                draw(vertices=Buffer[0], indices=Buffer[1], program=Program(name=BatchBuilder2D.Tinted, attributes=[a_Tex, a_Col, a_Pos], uniforms=[u_ProjMat, u_Tex]), type=TRIANGLES, vertexLayout=VertexLayout[a_Pos, a_Tex, a_Col], vertexCount=12, offset=0, blending=BlendFactors(srcRGB=SOURCE_ALPHA, dstRGB=ONE_MINUS_SOURCE_ALPHA, srcA=ONE, dstA=ONE_MINUS_SOURCE_ALPHA), uniforms={Uniform(u_ProjMat)=Matrix4([0.125, 0.0, 0.0, 0.0, 0.0, -0.125, 0.0, 0.0, 0.0, 0.0, -1.0, 0.0, -1.0, 1.0, -0.0, 1.0]), Uniform(u_Tex)=TextureUnit(texture=Texture[0], linear=true)})
                ::draw.indices=[0, 1, 2, 3, 0, 2, 4, 5, 6, 7, 4, 6]
                ::draw.vertex[0]: a_Pos[vec2(0.0,0.0)], a_Tex[vec2(0.0,0.0)], a_Col[byte4(-1)]
                ::draw.vertex[1]: a_Pos[vec2(100.0,0.0)], a_Tex[vec2(1.0,0.0)], a_Col[byte4(-1)]
                ::draw.vertex[2]: a_Pos[vec2(100.0,100.0)], a_Tex[vec2(1.0,1.0)], a_Col[byte4(-1)]
                ::draw.vertex[3]: a_Pos[vec2(0.0,100.0)], a_Tex[vec2(0.0,1.0)], a_Col[byte4(-1)]
                ::draw.vertex[4]: a_Pos[vec2(100.0,0.0)], a_Tex[vec2(0.0,0.0)], a_Col[byte4(-1)]
                ::draw.vertex[5]: a_Pos[vec2(200.0,0.0)], a_Tex[vec2(1.0,0.0)], a_Col[byte4(-1)]
                ::draw.vertex[6]: a_Pos[vec2(200.0,100.0)], a_Tex[vec2(1.0,1.0)], a_Col[byte4(-1)]
                ::draw.vertex[7]: a_Pos[vec2(100.0,100.0)], a_Tex[vec2(0.0,1.0)], a_Col[byte4(-1)]
                Buffer[1].close()
                Buffer[0].close()
			""".trimIndent(),
                ag.getLogAsString()
        )
    }
}
