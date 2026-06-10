package com.example.articlereview.model

object ArticleDataSource {

    fun getSampleArticles(): List<ArticleReview> = listOf(
        ArticleReview(
            id = 1,
            title = "The Attention Economy Is Eating Itself",
            author = "L.M. Sacasas",
            source = "Substack",
            sourceUrl = "https://theconvivialsociety.substack.com",
            coverTag = "Philosophy",
            readingTime = 12,
            dateRead = "May 8, 2025",
            rating = 4.5f,
            shortSummary = "A sharp critique on how attention is commodified and how we unwittingly surrender our inner life to platforms engineered for endless engagement.",
            fullReview = """
                Sacasas opens with a deceptively simple provocation: what if the problem isn't that we are distracted, but that distraction has become the product? He traces the lineage of attention from William James through to contemporary UX design, showing how the concept shifted from a cognitive faculty to an economic unit.

                The essay is structured around three tensions. First, the conflict between *depth* and *breadth* in information consumption — the platform incentive always favors breadth, because breadth scales, depth doesn't. Second, the tension between *presence* and *performance*: social media turns every act of attention into a display of attention, hollowing out genuine engagement. Third, and most provocatively, the tension between *rest* and *productivity* — he argues the attention economy has colonized leisure itself, making even downtime into content-producing time.

                What elevates this above the typical digital-detox think-piece is Sacasas's refusal to offer easy solutions. He resists the lifestyle-optimization framework entirely. His closing argument — that the problem is structural, not personal — is both deflating and clarifying. You cannot hack your way to genuine attention; the environment itself must change.

                The prose is dense but rewarding, written for readers already familiar with thinkers like Simone Weil and Ivan Illich. Those unfamiliar may find certain references opaque, but the core argument holds on its own.
            """.trimIndent(),
            keyTakeaways = listOf(
                "Attention is not a personal failing to fix but an economic resource being extracted.",
                "Social platforms convert presence into performance, undermining genuine engagement.",
                "The digital-detox paradigm is a category error — it treats a systemic problem as individual.",
                "Leisure and rest have been colonized by the attention economy, leaving no refuge."
            ),
            recommendedFor = "Readers interested in media criticism, philosophy of technology, or the ethics of design.",
            reviewerMood = "Thought-provoking"
        ),
        ArticleReview(
            id = 2,
            title = "Why Smart People Believe Stupid Things",
            author = "Michael Shermer",
            source = "Medium",
            sourceUrl = "https://medium.com",
            coverTag = "Psychology",
            readingTime = 8,
            dateRead = "May 5, 2025",
            rating = 3.5f,
            shortSummary = "An exploration of belief formation, motivated reasoning, and why high intelligence doesn't confer immunity to epistemic error — sometimes it makes it worse.",
            fullReview = """
                Shermer's central claim — that intelligence amplifies belief rather than corrects it — is well-established in the literature, but he presents it accessibly without sacrificing precision. The article draws on his own research and Jonathan Haidt's moral foundations theory to build the argument.

                The standout concept here is the *smart idiot effect*: the empirical finding that highly educated partisans hold more extreme and more confident wrong beliefs than their less-educated counterparts. The mechanism is elegant: intelligence doesn't reduce motivated reasoning, it provides better tools for rationalization.

                Shermer identifies three interlocking factors — pattern recognition bias (seeing meaningful patterns in noise), attribution bias (explaining others' failure differently from our own), and the emotional architecture of conviction itself. Beliefs that feel good to hold are held more stubbornly.

                The article falters somewhat in its prescriptive section, where the advice (be humble, seek disconfirmation) is sound but thin. There's also a slight irony in an article about epistemic overconfidence that feels rather confident about a contested scientific domain. Still, the descriptive sections are strong and the writing is clean.
            """.trimIndent(),
            keyTakeaways = listOf(
                "Intelligence amplifies motivated reasoning rather than correcting it.",
                "The 'smart idiot effect' shows educated partisans hold more extreme wrong beliefs.",
                "Beliefs with emotional resonance are the hardest to dislodge with evidence.",
                "Intellectual humility is a practice, not a trait — it requires deliberate cultivation."
            ),
            recommendedFor = "Anyone interested in cognitive science, epistemology, or understanding political polarization.",
            reviewerMood = "Clarifying"
        ),
        ArticleReview(
            id = 3,
            title = "The Case for Boredom",
            author = "Tosin Thompson",
            source = "Aeon",
            sourceUrl = "https://aeon.co",
            coverTag = "Wellbeing",
            readingTime = 10,
            dateRead = "Apr 29, 2025",
            rating = 5.0f,
            shortSummary = "A genuine rehabilitation of boredom as a productive mental state — not an enemy of meaning but one of its necessary conditions.",
            fullReview = """
                This is the best essay I've read this year. Thompson's argument is simple on the surface: boredom, properly understood, is not the absence of stimulation but the presence of an orientation toward meaning-seeking. But the execution is layered and surprising throughout.

                She opens with a striking phenomenological observation: boredom is the only emotion where the object of our discomfort is time itself. We aren't bored *by* something, we are bored *in* something — a state where temporal experience becomes unbearably present. This reframing unlocks everything that follows.

                The historical survey is fascinating. Boredom as a named experience is surprisingly recent — the word enters English in the 18th century. Before that, what we call boredom was closer to *acedia*, the spiritual listlessness described by medieval monks as an enemy of contemplative life. Thompson suggests modern boredom is acedia's secular cousin, a signal that our current activity is misaligned with our deeper orientation.

                The neuroscience section is the weakest — she gestures at default mode network research without fully engaging with the literature — but it doesn't undermine the philosophical argument. Her conclusion, that smartphones have stolen boredom from us and replaced it with distraction (which is categorically different), is exactly right.

                The writing is superb. Every paragraph earns its place.
            """.trimIndent(),
            keyTakeaways = listOf(
                "Boredom is not stimulus-absence but a form of temporal self-awareness.",
                "The historical concept of 'acedia' reveals boredom as a spiritual-existential signal.",
                "Distraction and boredom are categorically different — distraction prevents the productive discomfort boredom provides.",
                "Smartphones haven't cured boredom; they've prevented it, depriving us of a generative state."
            ),
            recommendedFor = "Everyone. Particularly those who feel compelled to fill every idle moment.",
            reviewerMood = "Inspiring"
        ),
        ArticleReview(
            id = 4,
            title = "How to Think About the Future of Work",
            author = "Zeynep Ton",
            source = "MIT Sloan Review",
            sourceUrl = "https://sloanreview.mit.edu",
            coverTag = "Economics",
            readingTime = 15,
            dateRead = "Apr 22, 2025",
            rating = 4.0f,
            shortSummary = "A rigorous challenge to the gloomy consensus on automation and jobs, arguing that the real threat is low-quality work rather than job elimination.",
            fullReview = """
                Ton's argument cuts against the grain of both the techno-optimists and the displacement pessimists. She's not interested in whether automation will take jobs — she's interested in what kind of jobs remain and whether they're worth having.

                Her central evidence base is her own research on the retail sector, where she found that the companies treating low-wage workers as strategic assets rather than cost centers consistently outperformed competitors on both financial and employee metrics. The good jobs strategy, as she calls it, turns out to be good business.

                The article extrapolates from this to the broader automation question: the real risk isn't that machines take all the jobs, but that automation is used as an excuse to hollow out the jobs that remain — reducing skill, autonomy, and judgment in human roles to make them easier to monitor and replace piecemeal.

                Her prescriptive framework is concrete: organizations should design human roles around judgment and relationships, not execution of routine tasks. The paper is dense with examples from Costco, Mercadona, and other retailers who have implemented this successfully.

                It reads somewhat academically — the prose isn't as vivid as the best long-form journalism — but the ideas are important and the evidence is solid.
            """.trimIndent(),
            keyTakeaways = listOf(
                "The primary risk of automation is job quality degradation, not job elimination.",
                "Treating workers as strategic assets rather than costs is financially sound.",
                "Good job design focuses on judgment and relationships, not task execution.",
                "The 'good jobs strategy' is documented to outperform low-road competitors."
            ),
            recommendedFor = "Managers, policy thinkers, and anyone interested in labor economics or organizational design.",
            reviewerMood = "Dense but Rewarding"
        ),
        ArticleReview(
            id = 5,
            title = "The Hidden Lives of Trees",
            author = "Ferris Jabr",
            source = "The New Yorker",
            sourceUrl = "https://newyorker.com",
            coverTag = "Science",
            readingTime = 18,
            dateRead = "Apr 15, 2025",
            rating = 4.5f,
            shortSummary = "A beautifully written investigation into forest ecology, mycorrhizal networks, and what the science of inter-tree communication actually says — versus what the popular press claims.",
            fullReview = """
                Jabr's piece is corrective journalism at its best. He takes the viral idea of the "wood wide web" — forests as cooperative communities sharing nutrients via fungal networks — and applies rigorous scrutiny without losing the genuine wonder underlying the science.

                The reporting is excellent. Jabr interviewed Suzanne Simard (whose research popularized the idea) alongside critics who challenge her interpretive framework, producing a genuinely balanced account. The central tension: the mycorrhizal network is real, the resource transfer is documented, but whether it constitutes "communication" or "care" is a category question, not an empirical one.

                This is where the article is most intellectually satisfying. Jabr argues that the popular telling of tree science is a kind of anthropomorphic projection — we want forests to be communities because we find community meaningful. The science doesn't require that interpretation, and in some ways the more austere ecological picture is richer.

                The prose is gorgeous throughout — Jabr is one of the best science writers working. A description of walking through an old-growth forest and understanding the fungal architecture underfoot is as good as anything in contemporary nonfiction.

                The piece runs long, but earns every word.
            """.trimIndent(),
            keyTakeaways = listOf(
                "Mycorrhizal networks are real and documented, but 'communication' is an interpretive frame, not a factual claim.",
                "Popular science often anthropomorphizes findings in ways the data doesn't require.",
                "Suzanne Simard's research is solid; the popular interpretation may exceed it.",
                "The austere ecological truth of forests is as compelling as the romantic version."
            ),
            recommendedFor = "Science enthusiasts, nature writers, and anyone who enjoyed 'Finding the Mother Tree'.",
            reviewerMood = "Wonderful"
        ),
        ArticleReview(
            id = 6,
            title = "Against Optimization",
            author = "James Stuber",
            source = "Substack",
            sourceUrl = "https://substack.com",
            coverTag = "Lifestyle",
            readingTime = 6,
            dateRead = "Apr 10, 2025",
            rating = 3.0f,
            shortSummary = "A short, punchy argument that the self-optimization mindset is itself a form of anxiety — and that genuine flourishing requires accepting irreducible messiness.",
            fullReview = """
                Stuber writes fast and thinks faster. This essay is intentionally brief — almost a provocation memo — and lands several solid punches in a short space.

                His target is the quantified-self movement and its philosophical assumptions: that human life is best understood as a system to be optimized, that flourishing can be decomposed into trackable metrics, and that the good life is a performance to be maximized. His counter: optimization frameworks are anxiety-management tools dressed up as productivity tools. The obsessive tracker is not becoming more effective — they are channeling existential unease into a legible system.

                The strongest passage draws on Aristotle's concept of *phronesis* — practical wisdom — as a form of knowledge that is irreducibly situational and cannot be systematized. You cannot app your way to good judgment.

                The article is too brief to fully develop these ideas, and some readers will want more evidence. But as a provocation, it succeeds. It's the kind of thing that stays with you and changes how you look at your habit-tracking apps.
            """.trimIndent(),
            keyTakeaways = listOf(
                "Self-optimization culture may be a form of anxiety management, not genuine improvement.",
                "Aristotelian phronesis (practical wisdom) cannot be reduced to trackable metrics.",
                "Metric-obsession can crowd out the judgment and spontaneity that make life worth living.",
                "Flourishing requires accepting irreducible mess — not engineering it away."
            ),
            recommendedFor = "Productivity enthusiasts willing to question their own habits.",
            reviewerMood = "Refreshing"
        )
    )
}
