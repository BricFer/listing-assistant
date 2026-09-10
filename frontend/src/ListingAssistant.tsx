import { useState } from 'react';
import { AiListingService } from './services/aiListingService';

export const ListingAssistant = () => {
  const [status, setStatus] = useState<'idle' | 'loading' | 'success' | 'error'>('idle');
  const [description, setDescription] = useState<string>('');
  const [error, setError] = useState<Error | null>(null);
  const [response, setResponse] = useState<AiListingResponse | null>(null);

  const handleGenerate = async () => {
    setStatus('loading');
    setError(null);

    try {
      const result = await AiListingService(description);
      setResponse(result);
      setStatus('success');
    } catch (e) {
      if (e instanceof Error) {
        setError(e);
        setStatus('error');
      }
    }
  }

  return (
    <div className='container flex column align-center justify-center gap-2'>
      <div className='border-style flex column justify-center  w-100'>
        <h4 className='title'>Listing Assistant</h4>
        <hr />

        <form
          className='flex column gap-1'
          onSubmit={(e) => {
            e.preventDefault();
            handleGenerate();

          }} >
          <input
            className='input border-style'
            type='text'
            placeholder='What you want to sell today?'
            name='description'
            autoComplete='off'
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />

          <button
            className='w-28 h-4 btn shadow'
            type='submit'
            disabled={status === 'loading'}
          >Generate</button>
        </form>

      </div>

      <div className='border-style w-100'>
        {status === 'loading' && <p>I'm preparing your ad</p>}
        {status === 'error' && <p>{error?.message}</p>}
        {
          status === 'success' &&
          <div className='flex column gap-2'>
            <h3>{response?.title}</h3>
            <div>
              <p className='bold mb-1'>Suggested tags:</p>
              <div className='flex gap-1'>
                {response?.tags.map((tag, key) => (
                  <p
                    className='border-style-tag'
                    key={tag + key}
                  >{tag}</p>
                ))}
              </div>
            </div>
            <div>
              <p className = 'bold'>Price range:</p>
              <p>{response?.minPrice}€ - {response?.maxPrice}€</p>
            </div>
          </div>
        }
      </div>
    </div>
  )
}
