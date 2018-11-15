package me.chill.queries.playlist

import khttp.put
import me.chill.exceptions.SpotifyQueryException
import me.chill.queries.AbstractQuery
import me.chill.utility.request.Header
import me.chill.utility.request.RequestMethod
import me.chill.utility.request.responseCheck
import java.io.File
import java.util.*

class UploadCustomPlaylistImageQuery private constructor(
  private val accessToken: String,
  private val playlistId: String,
  private val cover: String) : AbstractQuery<Boolean>(accessToken, RequestMethod.Get, "playlists", playlistId, "images") {

  // Special request type
  override fun execute(): Boolean {
    val headers = Header.Builder().accessToken(accessToken).contentType(Header.Builder.ContentType.Image).build().generate()

    val response = put(endpoint, headers, data = cover)
    response.responseCheck()

    return response.statusCode == 202
  }

  class Builder(private val accessToken: String, private val playlistId: String) {
    private val imageFileExtensions = listOf("jpg", "jpeg", "png", "gif")

    private var cover: String? = null

    fun cover(coverImageFilePath: String): Builder {
      cover = convertImageToBase64(coverImageFilePath)
      return this
    }

    fun build(): UploadCustomPlaylistImageQuery {
      cover ?: throw SpotifyQueryException("Cover image must be set")

      return UploadCustomPlaylistImageQuery(accessToken, playlistId, cover!!)
    }

    /**
     * Converts an image file to a base64 representation.
     *
     * @param imageFilePath File path to image to be converted
     * @throws SpotifyQueryException if the file given does not exist
     * @throws SpotifyQueryException if the file path is a directory
     * @throws SpotifyQueryException if the file's extension is not an allowed image extension
     * @throws SpotifyQueryException if the image file exceeds 256KB
     * @return The base64 representation of the image
     */
    private fun convertImageToBase64(imageFilePath: String) =
      Base64.getEncoder().encodeToString(
        verifyImageFile(
          verifyFilePath(imageFilePath)
        ).readBytes()
      )

    /**
     * Verifies that a given image file matches the criteria for being sent, ie, the file size cannot exceed 256KB.
     *
     * @param imageFile The image file to perform verification on
     * @throws SpotifyQueryException if the image file exceeds 256KB
     * @return The image file
     */
    private fun verifyImageFile(imageFile: File): File {
      val maximumFileSize = 256000
      if (imageFile.length() > maximumFileSize) throw SpotifyQueryException("Image file is too large")
      return imageFile
    }

    /**
     * Verifies that a given file path is a valid file path for an image file.
     *
     * @param filePath The file path to verify
     * @throws SpotifyQueryException if the file given does not exist
     * @throws SpotifyQueryException if the file path is a directory
     * @throws SpotifyQueryException if the file's extension is not an allowed image extension
     * @return File from the file path provided
     */
    private fun verifyFilePath(filePath: String): File {
      val file = File(filePath)
      if (!file.exists()) throw SpotifyQueryException("File path provided does not exist")
      if (!file.isFile) throw SpotifyQueryException("File path must lead to a file")
      if (!imageFileExtensions.contains(file.extension)) throw SpotifyQueryException("File with extension ${file.extension} is not a valid image")

      return file
    }
  }
}