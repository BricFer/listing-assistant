import { describe, it, expect, vi } from 'vitest'
import { AiListingService } from './aiListingService'

describe('AiListingService', () => {
  it('throws an error with the backend message when response is not ok', async () => {
    // Replace global fetch with a fake version
    globalThis.fetch = vi.fn().mockResolvedValue({
      ok: false,
      text: () => Promise.resolve('The AI response has an invalid format'),
    })

    // Act & Assert: verificar que la función lanza el error esperado
    await expect(AiListingService('some description')).rejects.toThrow('The AI response has an invalid format')
  })
})