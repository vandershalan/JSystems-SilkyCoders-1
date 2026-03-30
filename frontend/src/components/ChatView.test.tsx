import { describe, it, expect, vi, beforeEach } from 'vitest'
import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import ChatView from './ChatView'

describe('ChatView', () => {
  const mockOnSessionInvalid = vi.fn()
  const mockSessionId = 'test-session-123'

  beforeEach(() => {
    vi.clearAllMocks()
    localStorage.clear()
    // Mock fetch to return session info
    globalThis.fetch = vi.fn(() =>
      Promise.resolve({
        ok: true,
        json: () =>
          Promise.resolve({
            intent: 'RETURN',
            productName: 'Test Product',
            orderNumber: 'PL123',
          }),
      }),
    ) as unknown as typeof fetch
  })

  it('renders "Nowa sesja" button', () => {
    render(<ChatView sessionId={mockSessionId} onSessionInvalid={mockOnSessionInvalid} />)

    const newSessionButton = screen.getByRole('button', { name: 'Nowa sesja' })
    expect(newSessionButton).toBeInTheDocument()
  })

  it('"Nowa sesja" click calls onSessionInvalid callback', async () => {
    const user = userEvent.setup()
    render(<ChatView sessionId={mockSessionId} onSessionInvalid={mockOnSessionInvalid} />)

    const newSessionButton = screen.getByRole('button', { name: 'Nowa sesja' })
    await user.click(newSessionButton)

    expect(mockOnSessionInvalid).toHaveBeenCalled()
  })

  it('fetches session info on mount', async () => {
    render(<ChatView sessionId={mockSessionId} onSessionInvalid={mockOnSessionInvalid} />)

    await waitFor(() => {
      expect(globalThis.fetch).toHaveBeenCalledWith(`/api/sessions/${mockSessionId}`)
    })
  })

  it('handles 404 response by calling onSessionInvalid', async () => {
    globalThis.fetch = vi.fn(() =>
      Promise.resolve({
        ok: false,
        status: 404,
        json: () => Promise.resolve({ error: 'Session not found' }),
      }),
    ) as unknown as typeof fetch

    render(<ChatView sessionId={mockSessionId} onSessionInvalid={mockOnSessionInvalid} />)

    await waitFor(() => {
      expect(mockOnSessionInvalid).toHaveBeenCalled()
    })
  })
})
