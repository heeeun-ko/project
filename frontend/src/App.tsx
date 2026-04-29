import './App.css'

const newsItems = [
  {
    source: '매일경제',
    title: 'AI 반도체 투자 확대, 장비주 중심 수혜 기대',
    mood: '긍정',
  },
  {
    source: '한국경제',
    title: '환율 변동성 확대에 수출 대형주 관망세',
    mood: '중립',
  },
  {
    source: '서울경제',
    title: '탄소세 논의 재점화, 에너지 업종 비용 부담 점검',
    mood: '주의',
  },
]

const portfolio = [
  { name: '삼성전자', broker: '미래에셋', value: '4,820,000원', rate: '+6.8%' },
  { name: '현대차', broker: 'NH투자', value: '2,140,000원', rate: '+2.1%' },
  { name: 'NAVER', broker: '키움', value: '1,760,000원', rate: '-1.4%' },
]

const terms = ['온디바이스 AI', '탄소세', '스테이블코인']

function App() {
  return (
    <main className="app-shell">
      <header className="topbar">
        <a className="brand" href="/" aria-label="Finset 홈">
          <span className="brand-mark">F</span>
          <span>Finset</span>
        </a>
        <nav className="nav-links" aria-label="주요 메뉴">
          <a href="#news">News</a>
          <a href="#study">Study</a>
          <a href="#asset">Asset</a>
          <a href="#advisor">Advisor</a>
        </nav>
        <button className="ghost-button" type="button">
          시작하기
        </button>
      </header>

      <section className="hero-section" aria-labelledby="hero-title">
        <div className="hero-copy">
          <p className="eyebrow">개인 투자자를 위한 경제 루틴</p>
          <h1 id="hero-title">뉴스부터 포트폴리오까지, 오늘 볼 것만 깔끔하게.</h1>
          <p className="hero-description">
            쏟아지는 경제 뉴스를 AI가 요약하고, 내 보유 종목과 연결해 지금 필요한
            학습과 투자 점검을 한 화면에 모아줍니다.
          </p>
          <div className="hero-actions">
            <button className="primary-button" type="button">
              오늘 브리핑 보기
            </button>
            <button className="secondary-button" type="button">
              포트폴리오 추가
            </button>
          </div>
        </div>

        <div className="briefing-panel" aria-label="오늘의 시장 브리핑">
          <div className="panel-header">
            <span>Today Brief</span>
            <strong>09:30 업데이트</strong>
          </div>
          <div className="market-score">
            <span>AI 시장 영향도</span>
            <strong>+72</strong>
          </div>
          <div className="score-track">
            <span style={{ width: '72%' }} />
          </div>
          <div className="briefing-grid">
            <div>
              <span>보유 종목 관련 뉴스</span>
              <strong>8건</strong>
            </div>
            <div>
              <span>학습 추천</span>
              <strong>3개</strong>
            </div>
          </div>
        </div>
      </section>

      <section className="feature-grid" aria-label="주요 기능">
        <article className="feature-card" id="news">
          <div className="section-label">Smart News</div>
          <h2>신문사별 뉴스 피드</h2>
          <p>언론사 필터와 Gemini 3줄 요약으로 시장 영향을 빠르게 확인합니다.</p>
          <div className="news-list">
            {newsItems.map((item) => (
              <div className="news-item" key={item.title}>
                <span>{item.source}</span>
                <strong>{item.title}</strong>
                <em className={`mood ${item.mood === '긍정' ? 'positive' : ''}`}>
                  {item.mood}
                </em>
              </div>
            ))}
          </div>
        </article>

        <article className="feature-card compact" id="study">
          <div className="section-label">Daily Study</div>
          <h2>오늘의 학습</h2>
          <p>매일 경제 용어 3개와 신뢰도 높은 유튜브 영상을 추천합니다.</p>
          <div className="term-list">
            {terms.map((term) => (
              <span key={term}>{term}</span>
            ))}
          </div>
          <div className="video-preview">
            <span>추천 영상</span>
            <strong>2026 시장을 읽는 AI와 금리의 연결고리</strong>
          </div>
        </article>

        <article className="feature-card compact" id="asset">
          <div className="section-label">Virtual Asset</div>
          <h2>가상 주식 대시보드</h2>
          <p>종목명, 매수금액, 증권사만 입력해 수익률을 간단히 추적합니다.</p>
          <div className="portfolio-list">
            {portfolio.map((stock) => (
              <div className="stock-row" key={stock.name}>
                <div>
                  <strong>{stock.name}</strong>
                  <span>{stock.broker}</span>
                </div>
                <div>
                  <strong>{stock.value}</strong>
                  <em className={stock.rate.startsWith('+') ? 'up' : 'down'}>{stock.rate}</em>
                </div>
              </div>
            ))}
          </div>
        </article>

        <article className="feature-card advisor-card" id="advisor">
          <div className="section-label">Gemini Advisor</div>
          <h2>투자 상담소</h2>
          <p>보유 종목과 최신 뉴스를 문맥으로 묶어 분석형 또는 멘탈 케어형 답변을 제공합니다.</p>
          <div className="advisor-chat">
            <div className="chat-bubble user">삼성전자 계속 보유해도 괜찮을까?</div>
            <div className="chat-bubble ai">
              냉철한 분석 모드로 보면 반도체 수요 회복은 긍정적이지만 환율과 밸류에이션을
              함께 점검해야 합니다.
            </div>
          </div>
          <small>투자 판단과 책임은 사용자 본인에게 있습니다.</small>
        </article>
      </section>
    </main>
  )
}

export default App
